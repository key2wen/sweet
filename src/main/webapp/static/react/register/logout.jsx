
import 'antd/lib/index.css';
import React from 'react';
import Ajax from '../ajax.jsx';
import { Row } from 'antd';

export default class Logout extends React.Component {

  static propTypes = {
    setStep : React.PropTypes.func.isRequired
  }

  logout = () => {
    Ajax.post({
      url: "/user/logout"
    }).then(() => {
      this.props.setStep(0);
    });
  }

  render() {
    return (
      <div>
        <Row type="flex" justify="end">
          <a href="#" onClick={ this.logout }
             style={{ display:'inline-block', marginTop: '10px', textDecoration:'underline', color: 'gray' }}>退出登录！</a>
        </Row>
      </div>
    );
  }
}
