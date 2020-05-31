var ExtractTextPlugin = require('extract-text-webpack-plugin');
var webpack = require("webpack");
var path = require("path");

module.exports = {
  context: path.resolve(__dirname, "src"),

  entry: './js/app.js',

  output: {
    path: './public',
    filename: './js/app.js'
  },
  resolve: {
    modulesDirectories: [
      "node_modules",
      path.resolve(__dirname, "src")
    ],
    extensions: ['', '.js']
  },
  module: {
    loaders: [
      {
        test: /\.css$/,
        exclude: /node_modules/,
        loader: ExtractTextPlugin.extract(['css-loader?sourceMap'])
      },
      {
        test: /\.hbs$/,
        loader: "handlebars-loader",
        query: {
          inlineRequires: 'images\/'
        }
      },
      {
        test: /\.scss$/,
        loader: ExtractTextPlugin.extract("style", "css!sass")
      },
      {
        test: /\.(woff2?|svg)$/,
        loader: "url-loader",
        include: path.resolve(__dirname, "node_modules", "bootstrap-sass", "assets"),
        query: {
          limit: '10000',
          name: '/css/fonts/[name].[ext]'
        }
      },
      {
        test: /\.(ttf|eot)$/,
        loader: "file-loader",
        include: path.resolve(__dirname, "node_modules", "bootstrap-sass", "assets"),
        query: {
          name: '/css/fonts/[name].[ext]'
        }
      },
      {
        test: /\.(png|jpg|svg|ttf|eot|woff|woff2)$/,
        include: path.resolve(__dirname, 'src', 'images'),
        loader: 'file-loader',
        query: {
          name: "/[path][name].[ext]",
          limit: 8192
        }
      }
    ]
  },

    devtool: 'source-map',
    plugins: [
      new ExtractTextPlugin('./css/main.css'),
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery"
      })
    ]
};