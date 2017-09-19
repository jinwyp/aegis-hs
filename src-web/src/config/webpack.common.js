/**
 * Created by jin on 7/24/17.
 */


const path                 = require('path')
const webpack              = require('webpack')
const {CommonsChunkPlugin} = require('webpack').optimize;
const AssetsPlugin         = require('assets-webpack-plugin')
const {CheckerPlugin}      = require('awesome-typescript-loader')
const ngcWebpack           = require('ngc-webpack')

const helpers = require('./helpers')


const vendorLibraryFromChunks = [
     "login", "adminHome"
]

const distPath = {
    assetsPluginPath: '../dist/rev-manifest',
    assetsPluginFilename : 'rev-manifest-js.json'
}


module.exports = function (env) {
    env = env || 'dev'

    console.log('---------- Webpack env:', env)

    const AOT = env === 'aot'?  true : false
    const isDev = env === 'dev'?  true : false

    console.log('---------- Angular Build Using AOT:', AOT, ' ----------')


    return {
        entry: {
            "polyfills": ["./ts/polyfills.ts"],
            "login": AOT ? './ts/page/login.aot.ts' : './ts/page/login.ts',
            "adminHome": AOT ? './ts/page/adminHome.aot.ts' : './ts/page/adminHome.ts'
        },

        /**
         * Options affecting the output of the compilation.
         *
         * See: http://webpack.github.io/docs/configuration.html#output
         */

        output: {
            /**
             * The output directory as absolute path (required).
             *
             * See: http://webpack.github.io/docs/configuration.html#output-path
             */
            path: path.join(process.cwd(), "../dist/static/js"),

            /**
             * Specifies the name of each output file on disk.
             * IMPORTANT: You must not specify an absolute path here!
             *
             * See: http://webpack.github.io/docs/configuration.html#output-filename
             */
            filename: isDev ? "[name].bundle.js" : "[name].bundle.js",

            /**
             * The filename of the SourceMaps for the JavaScript files.
             * They are inside the output.path directory.
             *
             * See: http://webpack.github.io/docs/configuration.html#output-sourcemapfilename
             */
            sourceMapFilename: "[file].map",

            /** The filename of non-entry chunks as relative path
             * inside the output.path directory.
             *
             * See: http://webpack.github.io/docs/configuration.html#output-chunkfilename
             */
            chunkFilename: isDev ?  "[id].chunk.js" : "[name].chunk.js"
        },

        resolve : {
            "extensions" : [ ".ts",  ".js" ],
            "modules"    : [ "./node_modules" ],
            "symlinks"   : true  // Whether to resolve symlinks to their symlinked location. Default: true. https://webpack.js.org/configuration/resolve/
        },



        module: {

            rules: [

                /**
                 * Typescript loader support for .ts
                 *
                 * Component Template/Style integration using `angular2-template-loader`
                 * Angular 2 lazy loading (async routes) via `ng-router-loader`
                 *
                 * `ng-router-loader` expects vanilla JavaScript code, not TypeScript code. This is why the
                 * order of the loader matter.
                 *
                 * See: https://github.com/s-panferov/awesome-typescript-loader
                 * See: https://github.com/TheLarkInn/angular2-template-loader
                 * See: https://github.com/shlomiassaf/ng-router-loader
                 */

                {
                    test: /\.ts$/,
                    use: [
                        {
                            loader: 'awesome-typescript-loader',
                            options: {
                                configFileName: helpers.root( 'tsconfig.json')
                            }
                        },
                        {
                            loader: 'ngc-webpack',
                            options: {
                                disable: !AOT,                   // SET TO TRUE ON NON AOT PROD BUILDS
                            }
                        },
                        {
                            loader: 'angular2-template-loader'
                        }
                    ],
                    exclude: [/\.(spec|e2e)\.ts$/]
                },


                /**
                 * Raw loader support for *.html
                 * Returns file content as string
                 *
                 * See: https://github.com/webpack/raw-loader
                 */
                {
                    test: /\.(html|css)$/,
                    loader : "raw-loader"
                },


                /**
                 * Json loader support for *.json files.
                 *
                 * See: https://github.com/webpack/json-loader
                 */
                {
                    test   : /\.json$/,
                    loader : "json-loader"
                }


            ]
        },

        plugins       : [


            /**
             * Plugin: DefinePlugin
             * Description: Define free variables.
             * Useful for having development builds with debug logging or adding global constants.
             *
             * Environment helpers
             *
             * See: https://webpack.github.io/docs/list-of-plugins.html#defineplugin
             */
            // NOTE: when adding more properties make sure you include them in custom-typings.d.ts


            new webpack.DefinePlugin({
                'process.env.NODE_ENV': isDev ? JSON.stringify('development') : JSON.stringify('production')
            }),




            new AssetsPlugin({
                path: helpers.root(distPath.assetsPluginPath),
                filename: distPath.assetsPluginFilename,
                prettyPrint: true,
                processOutput : function (assets) {
                    let output = {}
                    for (const prop in assets) {
                        if (Object.prototype.hasOwnProperty.call(assets, prop)) {
                            output[prop + '.bundle.js'] = assets[prop].js
                        }
                    }
                    return JSON.stringify(output, null, 2)
                }
            }),

            /**
             * Plugin: ContextReplacementPlugin
             * Description: Provides context to Angular's use of System.import
             *
             * See: https://webpack.github.io/docs/list-of-plugins.html#contextreplacementplugin
             * See: https://github.com/angular/angular/issues/11580
             */

            new webpack.ContextReplacementPlugin(
                // The (\\|\/) piece accounts for path separators in *nix and Windows
                /angular(\\|\/)core(\\|\/)@angular/,
                helpers.root('./'), // location of your src
                {
                    /**
                     * Your Angular Async Route paths relative to this root directory
                     */

                } // a map of your routes
            ),


            /**
             * Plugin: ForkCheckerPlugin
             * Description: Do type checking in a separate process, so webpack don't need to wait.
             *
             * See: https://github.com/s-panferov/awesome-typescript-loader#forkchecker-boolean-defaultfalse
             */
            new CheckerPlugin(),




            new CommonsChunkPlugin({
                minChunks : 2,
                async     : "common"
            }),


            new CommonsChunkPlugin({
                name: 'polyfills',
                chunks: ['polyfills']
            }),


            /**
             * This enables tree shaking of the vendor modules
             */
            new CommonsChunkPlugin({
                name      : "vendor",
                chunks    : vendorLibraryFromChunks,
                minChunks: module => /node_modules/.test(module.resource),
            }),

            new CommonsChunkPlugin({
                name: ['manifest'],
                minChunks: Infinity,
            }),




            new ngcWebpack.NgcWebpackPlugin({
                /**
                 * If false the plugin is a ghost, it will not perform any action.
                 * This property can be used to trigger AOT on/off depending on your build target (prod, staging etc...)
                 *
                 * The state can not change after initializing the plugin.
                 * @default true
                 */
                disabled: !AOT,
                tsConfig: helpers.root('tsconfig.aot.json')
            })


        ],


        /**
         * Include polyfills or mocks for various node stuff
         * Description: Node configuration
         *
         * See: https://webpack.github.io/docs/configuration.html#node
         */
        node: {
            global: true,
            crypto: 'empty',
            process: true,
            module: false,
            clearImmediate: false,
            setImmediate: false
        }
    }
}
