import 'antd/style/index.less';
import '../../less/exam.less';
import React from 'react';
import ReactDOM from 'react-dom';
import { Button, Icon, Alert, Row, Col } from 'antd';
import $ from 'jquery';

export default class Entry extends React.Component {

  handleClick() {
    this.props.handleClick();
  }

  render() {

    let startBtn = global.code === 0 ?
      <Button size="large" type="ghost" onClick={ this.handleClick.bind(this) }> 开 始 考 试 </Button> :
      <Button size="large" type="ghost" disabled> 开 始 考 试 </Button>

    let tips = [];
    if (global.code === 0) {
      tips.push(
        <Alert message="您已达到考试条件"
               description="您已完成所有的学习任务, 可以开始考试了, 考试合格, 即可获得相应的学时"
               type="success"
               showIcon/>
      );
    }
    if (global.code === 1) {
      tips.push(
        <Alert message="暂时没有考试"
               description="敬请期待"
               type="error"
               showIcon/>
      );
    }
    if (global.code === 2) {
      tips.push(
        <Alert message="考试已关闭"
               description="最近一次考试已经关闭"
               type="error"
               showIcon/>
      );
    }
    if (global.code === 3) {
      tips.push(
        <Alert message="您未达到考试条件"
               description={<span>请最少完成<font color="red"> { global.leastRead } </font> 篇文章的学习任务<br/> 您当前已学习: <font color="red">{ global.hasRead }</font> 篇</span>}
               type="error"
               showIcon/>
      );
    }
    if (global.user) {
      tips.push(
        <Alert message="个人信息"
               description={(<div>用户名: { global.user.username } <br/> 姓名: { global.user.name } </div>)}
               type="info"
               showIcon/>
      )
    }
    if (global.exam) {
      tips.push(
        <Alert message="考试信息"
               description={(<div>考试名称: {global.exam.name} <br/>
               考试时间: <font color="red">30 </font> 分钟<br/>
               题目: <font color="red"> {global.exam.questionCount} </font>道单选题 <br/>
               及格标准: 答对 <font color="red"> { global.exam.passCount } </font> 题 ( { parseInt(global.exam.passScore) } 分 ) <br/> </div>)}
               type="warn"
               showIcon/>
      )
    }

    tips = tips.map((e, i) => {
      return <div key={'tip_' + i}> {e} <br/></div>;
    })

    return (
      <div className="entry">
        <br/>
        <h3 style={{ color: 'gray' }}>天天微信课在线考试</h3>

        <div style={{ margin: '30px auto'  }}>
          { tips }
        </div>
        <div className="entry_button">
          { startBtn }
        </div>

      </div>
    );
  }
}
