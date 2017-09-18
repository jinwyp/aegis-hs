/**
 * Created by jin on 7/25/17.
 */


const webpackMerge = require('webpack-merge');
const webpack = require('webpack');

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
        devtool : 'cheap-module-source-map',


        plugins: [
            // new webpack.HotModuleReplacementPlugin()
        ],


        devServer : {
            hot: false,
            inline             : false,
            port               : 4200,
            historyApiFallback : true,
            contentBase        : helpers.root('ts/page'),

            watchOptions : {
                // if you're using Docker you may need this
                // aggregateTimeout: 300,
                // poll: 1000,
                ignored : /node_modules/
            },

            publicPath : "/static/js/",

            proxy : {
                "/api"    : {
                    "target" : "http://localhost:3000",
                    "secure" : false
                },
                "/web"    : {
                    "target" : "http://localhost:3000",
                    "secure" : false
                },
                "/static" : {
                    "target" : "http://localhost:3000",
                    "secure" : false
                }
            }
        }
    })
}
