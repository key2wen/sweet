import '../../css/article/mlist.css';
import React, { Component } from 'react';
import Pagination from '../pagination.jsx';

// 定义文章单个元素的展示
class Article extends Component {
  render() {
    const { title, coverImage, coverDesc, submitTime, author, hasRead, hasStudy, viewCount } = this.props.data;
    return (
      <li className="table-view-cell media">
        <a href={'/article/view/' + this.props.data.id} data-ignore="push">
          <div style={{ float: 'left', marginRight: '10px' }}><img src={ coverImage } width="60" height="60"/></div>
          <div className="media-body">
            <p style={{ fontSize: '16px', color: '#555', marginBottom: '5px' }}>{ title }</p>
            <p>
              { coverDesc }
              {
                hasStudy ?
                  <span className="badge" style={{float: 'right', background: '#ffffff', color: '#87d068'}}>
                    <span className="icon icon-check" style={{fontSize: '20px', width: '12px', marginLeft: '-4px'}}/>
                  </span> : ''}

            </p>

            <p style={{ fontSize: '12px'}}>
              { submitTime.substring(0, 10) } &nbsp;
              { author } &nbsp;&nbsp;&nbsp;
              <span style={{ color: '#aaa', fontSize: '12px' }}>
                <span className="icon icon-search" style={{ fontSize: '12px', width: '12px' }}/>
                { viewCount }
              </span>

            </p>

          </div>
          { /* { hasRead ? '' : <span className="badge pull-right">1</span> }*/ }

        </a>
      </li>
    );
  }
}

class MainPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currItem: this.props.items[0],
    }
  }

  handleClick(item) {
    this.setState({currItem: item});
  }

  render() {
    let domItems = [];
    this.props.items.forEach(item => {
      let itemClass = 'control-item';
      // 当前页设置class为选中样式
      if (this.state.currItem.id == item.id) {
        itemClass += ' active';
      }
      domItems.push(<span key={ item.id } className={ itemClass } url={ item.url }
                          onClick={ this.handleClick.bind(this, item) }>{ item.name }</span>)
    });
    return (
      <div className="content">
        <header className="bar bar-nav">
          <div className="segmented-control nav_tab">
            { domItems }
          </div>
        </header>

        <div className="content content-padded">
          <Pagination url={ this.state.currItem.url } elementType={ Article }/>
        </div>
      </div>
    )
  }
}

export default MainPage;






