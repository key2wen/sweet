import 'antd/lib/index.css';
import '../../css/register.css';
import React from 'react';
import { Alert, Button, Row, Col, message } from 'antd';
import Ajax from '../ajax.jsx';

export default class BindWeixin extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      canBind: false,
      reason: '无法绑定'
    }
    this.canBind = this.canBind.bind(this);
    this.handleBind = this.handleBind.bind(this);
    this.canBind();
  }

  canBind() {
    Ajax.get({
      url: "/user/canBind.json"
    }).then( jsonWrapper => {
      if (jsonWrapper && jsonWrapper.status && jsonWrapper.status.status_code === 0) {
        this.setState({ canBind: true });
        console.log(jsonWrapper);
      } else {
        this.setState({ canBind: false, reason: jsonWrapper.status.status_reason });
      }
    });
  }

  handleBind() {
    this.setState({canBind: false, reason: '绑定中...'});
    Ajax.post({
      url: "/user/bindWeixin.json"
    }).then( jsonWrapper => {
      if (jsonWrapper && jsonWrapper.status && jsonWrapper.status.status_code === 0) {
        message.success("绑定成功");
        this.setState({canBind: false, reason: '已绑定'});
      } else {
        message.error(jsonWrapper.status.status_reason);
        this.setState({canBind: true, reason: ''});
      }
    });
  }

  render() {
    let btn;
    if (this.state.canBind) {
      btn = <Button type="primary" style={{width: '250px'}} size="large" onClick={ this.handleBind } >绑定当前微信账号</Button>;
    } else {
      btn = <Button type="primary" size="large" style={{width: '250px'}} disabled>{this.state.reason}</Button>;
    }
    return (
      <div>
        <br/>
        <Alert message="已登陆"
               description="用户已登陆, 可以绑定微信账号"
               type="success"
               showIcon/>
        <br/>

        <Alert message="注意"
               description="只能绑定一个微信账号, 并且绑定之后不能更换"
               type="warn"
               showIcon/>
        <br/>

        <Alert
               description="绑定微信账号之后, 使用绑定账号的微信端进入天天微信课不需要登录"
               type="info"
               showIcon/>
        <br/>
        <br/>

        <Row type="flex" justify="center">
          <Col>
            {btn}
          </Col>
        </Row>

      </div>
    );
  }
}
