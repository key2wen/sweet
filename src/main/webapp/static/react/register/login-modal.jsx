import 'antd/lib/index.css';
import '../../css/register.css';
import React from 'react';
import Ajax from '../ajax.jsx';
import { Form, Input, Row, Col, Select, Button, message, Modal } from 'antd';

const FormItem = Form.Item;

export default class LoginModal extends React.Component {

  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleCancel = this.handleCancel.bind(this);
    this.showModal = this.showModal.bind(this);
    this.state = {
      visible: false,
      loading: false,
    }
  }

  handleSubmit(e) {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll(function (errors, values) {
      if (!!errors) {
        return;
      }
      this.setState({loading: true});
      console.log(values);
      Ajax.post({
        url: "/user/login",
        data: this.props.form.getFieldsValue()
      }).then(jsonWrapper => {
        if (jsonWrapper && jsonWrapper.status && jsonWrapper.status.status_code == 0) {
          message.success('登陆成功');
          this.setState({visible: false, loading: false});
          if (global.refUrl) {
            window.location = global.refUrl;
          } else {
            this.props.setStep();
          }
        } else {
          message.error(`登陆失败: ${jsonWrapper.status.status_reason}`);
          this.setState({loading: false});
        }
      });
    }.bind(this));
    return true;
  }

  handleCancel() {
    this.setState({visible: false, loading: false});
    this.props.form.setFieldsValue({username: '', password: ''});
  }

  showModal() {
    this.setState({
      visible: true
    });
  }

  render() {

    const { getFieldProps } = this.props.form;

    const formItemLayout = {
      labelCol: {span: 5},
      wrapperCol: {span: 18},
    };

    const usernameProps = getFieldProps('username', {
      rules: [
        {
          required: true,
          message: '请输入用户名',
        }
      ],
    });

    const passwordProps = getFieldProps('password', {
      rules: [
        {
          required: true,
          whitespace: true,
          message: '请填写密码',
        }
      ],
    });

    return (
      <div>
        <Row type="flex" justify="end">
          <a href="#" onClick={this.showModal}
             style={{ display:'inline-block', marginTop: '10px', textDecoration:'underline', color: 'gray' }}>直接去登录！</a>
        </Row>
        <Modal width="90%" style={{maxWidth: "450px"}}
               visible={this.state.visible}
               title="登录" onCancel={this.handleCancel}
               footer={[
                <Button key="back" type="ghost" size="large" onClick={this.handleCancel}>返 回</Button>,
                <Button key="submit" htmlType="submit" type="primary" size="large" loading={this.state.loading} onClick={this.handleSubmit}>
                  登 录
                </Button>
              ]}>
          <Form horizontal form={this.props.form} action="/user/register.json" method="post"
                onSubmit={this.handleSubmit}>

            <br/>
            <FormItem
              {...formItemLayout}
              label="用户名：">
              <Input {...usernameProps} placeholder="请输入用户名" ref="username"/>
            </FormItem>

            <FormItem
              {...formItemLayout}
              label="密码：">
              <Input {...passwordProps} type="password" autoComplete="off" placeholder="请输入密码" ref="password"/>
            </FormItem>

          </Form>
        </Modal>
      </div>
    );
  }
}
