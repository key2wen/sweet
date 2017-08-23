import React, { Component } from 'react';
import Ajax from './ajax.jsx';

export default class Pagination extends Component {
    /**
     * 构造方法
     */
    constructor(props) {
        super(props);
        // 将这个方法绑定在本实例中
        this.handleClick = this.handleClick.bind(this);
        this.initState();
        this.nextPage();
    }

    /**
     * 初始化状态
     */
    initState() {
        this.state = {
            pageNum: 1,
            pageSize: 10,
            loading: true,
            ended: false,
            list: []
        };
    }

    /**
     * 组件更新时调用
     * @Param prevProps 之前的属性
     * @Param prevState 之前的状态
     * @Override
     */
    componentDidUpdate(prevProps, prevState) {
        // 如果更新了url，则要重新初始化
        if (prevProps.url != this.props.url) {
            // 注意：setState() 不是同步方法，方法返回时，状态还没有更新
            // 要保证返回状态的一致，需要直接设置this.state的值，而不要调用setState方法
            this.initState();
            this.setState(this.state);
            this.nextPage();
        }
    }

    /**
     * 点击事件处理函数
     */
    handleClick() {
        // 立即重绘，让页面显示加载中...
        this.setState({loading: true});
        this.nextPage();
    }

    /**
     * 下一页
     */
    nextPage() {
        Ajax.get({
            url: this.props.url,
            data: {
                pageNum: this.state.pageNum,
                pageSize: (this.state.pageSize + 1)     // 多取一条数据，但不展示，用于判断是否到末尾
            }
        }).then(jsonWrapper => {
            this.state.loading = false;
            let len = jsonWrapper.result.list.length;
            // 如果没到结尾，那么len=pageSize+1，如果到了结尾，len <= pageSize
            if (len <= this.state.pageSize) {
                this.state.ended = true;
            } else {
                // 多出来的那一条并不显示
                len = this.state.pageSize;
            }
            for (let i = 0; i < len; i++) {
                let ele = jsonWrapper.result.list[i];
                let ElementType = this.props.elementType;
                // 将json结果放到状态机列表中
                this.state.list.push(<ElementType key={ele.id} data={ele}/>);
            }
            // 页号加一，重绘页面
            this.setState({pageNum: this.state.pageNum + 1});
        });
    }

    /**
     * 渲染
     * @Override
     */
    render() {
        var bottomTip;
        if (this.state.loading) {
            bottomTip = <span className="bottom_tip">加载中...</span>;
        } else if (this.state.ended) {
            bottomTip = <span className="bottom_tip">无更多结果</span>;
        } else {
            bottomTip = <a className="bottom_tip" onClick={this.handleClick}>显示更多</a>;
        }
        return (
            <div>
                <ul className="table-view">
                    {this.state.list}
                </ul>
                {bottomTip}
            </div>
        );
    }
}
