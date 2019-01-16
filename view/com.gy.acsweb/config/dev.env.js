'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
    NODE_ENV: '"development"',
    API_ROOT: '"http://localhost:8071"',
    accessKeyId: '"ZnKvvfgo9oyBGlJW"',
    accessKeySecret: '"WlEKUlkulSCJqbDFvZb0VP2QFpEsYl"',
    bucket: '"loverscamera"',
    region: '"oss-cn-shenzhen"',
    endpoint: '"oss-cn-shenzhen.aliyuncs.com"'
});
