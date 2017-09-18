/**
 * Created by jin on 7/25/17.
 */


const path             = require('path');
const webpackMerge     = require('webpack-merge');
const webpack          = require('webpack');
const OptimizeJsPlugin = require('optimize-js-plugin');


const commonConfig = require('./webpack.common.js');
const helpers = require('./helpers');


module.exports = function (env) {

    return webpackMerge(commonConfig(env), {

        /**
         * Developer tool to enhance debugging
         *
         * See: http://webpack.github.io/docs/configuration.html#devtool
         * See: https://github.com/webpack/docs/wiki/build-performance#sourcemaps
         */
        devtool: 'source-map',


        plugins: [

            new webpack.optimize.UglifyJsPlugin({ // https://github.com/angular/angular/issues/10618
                mangle: {
                    keep_fnames: true
                }
            }),


            /**
             * Webpack plugin to optimize a JavaScript file for faster initial load
             * by wrapping eagerly-invoked functions.
             *
             * See: https://github.com/vigneshshanmugam/optimize-js-plugin
             */
            new OptimizeJsPlugin({
              sourceMap: false
            })

        ]

    })

};
