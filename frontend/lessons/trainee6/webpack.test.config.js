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
        test: /\.hbs$/,
        loader: "handlebars-loader",
        query: {
          inlineRequires: 'images\/'
        }
      }
    ]
  },
  devtool: 'source-map',
  plugins: [
    new webpack.ProvidePlugin({
      $: "jquery",
      jQuery: "jquery"
    })
  ]
};