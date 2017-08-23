require('es5-shim');
require('es5-shim/es5-sham');
require('console-polyfill');
require('es6-promise');
require('fetch-ie8');

const React = require('react');
const ReactDOM = require('react-dom');
const MainPage = require('./article.jsx');

var items = [
  {
    id: 1,
    name: "待学习",
    url: "/article/list/need.json"
  },
  {
    id: 2,
    name: "本学期",
    url: "/article/list/term.json"
  },
  {
    id: 3,
    name: "全部",
    url: "/article/list/all.json"
  },
]

// 渲染, 分页对象需要传入一个url，表示数据来源
ReactDOM.render(
  <MainPage items={ items }/>,
  document.getElementById("main")
);
