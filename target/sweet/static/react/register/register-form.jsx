import 'antd/lib/index.css';
import '../../css/register.css';
import React from 'react';
import Ajax from '../ajax.jsx';
import { Form, Input, Row, Col, Button, message } from 'antd';
import AjaxSelect from '../ajax-select.jsx';
import EmailSelect from '../email-select.jsx';

const FormItem = Form.Item;
function noop() {
  return false;
}

export default class RegisterForm extends React.Component {

  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.state = {
      loading: false,
    };
  }

  getValidateStatus(field) {
    const { isFieldValidating, getFieldError, getFieldValue } = this.props.form;
    if (isFieldValidating(field)) {
      return 'validating';
    } else if (!!getFieldError(field)) {
      return 'error';
    } else if (getFieldValue(field)) {
      return 'success';
    }
  }

  userExists(rule, value, callback) {
    if (!value) {
      callback();
    } else {
      Ajax.get({
        url: '/user/checkUsername',
        data: {
          username: value,
        },
      }).then(jsonWrapper => {
        if (jsonWrapper.status.status_code === 0) {
          callback();
        } else {
          callback([new Error('抱歉，该用户名已被占用。')]);
        }
      });
    }
  }

  checkPassword(rule, value, callback) {
    const { validateFields } = this.props.form;
    if (value) {
      validateFields(['password2']);
    }
    callback();
  }

  checkPassword2(rule, value, callback) {
    const { getFieldValue } = this.props.form;
    if (value && value !== getFieldValue('password')) {
      callback('两次输入密码不一致！');
    } else {
      callback();
    }
  }

  handleSubmit(e) {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((errors) => {
      if (!!errors) {
        return;
      }
      this.setState({ loading: true });
      Ajax.post({
        url: '/user/register.json',
        data: this.props.form.getFieldsValue(),
      }).then(jsonWrapper => {
        if (jsonWrapper && jsonWrapper.status && jsonWrapper.status.status_code === 0) {
          message.success('注册成功');
          this.props.setStep(2);
        } else {
          message.error(`注册失败: ${jsonWrapper.status.status_reason}`);
          this.setState({ loading: false });
        }
      });
    });
    return true;
  }

  render() {
    const { getFieldProps, getFieldError, isFieldValidating } = this.props.form;

    const formItemLayout = {
      labelCol: {span: 6},
      wrapperCol: {span: 18},
    };

    const usernameProps = getFieldProps('username', {
      rules: [
        {
          required: true,
          min: 3,
          max: 20,
          message: '用户名长度只能在3-20位字符之间',
        }, {
          pattern: /^\w+$/,
          message: '用户名只能由英文、数字及下划线组成',
        }, {
          validator: this.userExists
        },
      ],
    });

    const passwordProps = getFieldProps('password', {
      rules: [
        {
          required: true,
          whitespace: true,
          message: '请填写密码',
        },
        {
          min: 6,
          max: 20,
          message: '密码长度只能在6-20位字符之间',
        },
        {
          validator: this.checkPassword.bind(this)
        },
      ],
    });

    const password2Props = getFieldProps('password2', {
      rules: [
        {
          required: true,
          whitespace: true,
          message: '请再次输入密码',
        }, {
          validator: this.checkPassword2.bind(this),
        }],
    });

    const nameProps = getFieldProps('name', {
      rules: [
        {
          required: true,
          min: 2,
          max: 20,
          message: '真实长度只能在2-20位字符之间',
        },
      ],
    });

    const wordDepartmentProps = getFieldProps('workDepartment', {
      rules: [
        {
          required: true,
          message: '请选择您的所在单位'
        }
      ],
    });

    const phoneProps = getFieldProps('phone', {
      rules: [
        {
          required: true,
          pattern: /^\d{11}$/,
          message: '只能输入11位数字'
        },
      ],
    });

    const emailProps = getFieldProps('email', {
      validate: [{
        rules: [
          {
            required: true,
            message: "请输入邮箱地址"
          },
        ],
        trigger: 'onBlur',
      }, {
        rules: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址'
          },
        ],
        trigger: ['onBlur', 'onChange'],
      }]
    });

    let registerBtn;
    if (this.props.closeRegister) {
      registerBtn = <Button type="primary" size="large" htmlType="submit" loading={this.state.loading} disabled>
        &nbsp;&nbsp;&nbsp;&nbsp;暂未开放注册&nbsp;&nbsp;&nbsp;&nbsp;</Button>;
    } else {
      registerBtn = <Button type="primary" size="large" htmlType="submit" loading={this.state.loading}>
        &nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册&nbsp;&nbsp;&nbsp;&nbsp;</Button>;
    }

    return (
      <Form horizontal form={this.props.form} action="/user/register.json" method="post"
            onSubmit={this.handleSubmit} ref="registerForm">

        <br />
        <FormItem
          {...formItemLayout}
          label="用户名："
          help={ isFieldValidating('name') ? '校验中...' : (getFieldError('username') || []).join(',') }
          hasFeedback
        >
          <Input {...usernameProps} placeholder="请输入用户名"/>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="密码："
          hasFeedback
        >
          <Input {...passwordProps} type="password" autoComplete="off" placeholder="请输入密码"
                                    onContextMenu={noop} onPaste={noop} onCopy={noop} onCut={noop}/>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="确认密码："
          hasFeedback
        >
          <Input {...password2Props} type="password" autoComplete="off" placeholder="请确认密码"
                                     onContextMenu={noop} onPaste={noop} onCopy={noop} onCut={noop}/>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="真实姓名："
          hasFeedback
        >
          <Input {...nameProps} placeholder="请输入真实姓名"/>
        </FormItem>

        <FormItem
          label="所在单位："
          {...formItemLayout}
          hasFeedback
        >
          <AjaxSelect { ...wordDepartmentProps } url="/work/list/all.json"
                                                 showSearch placeholder="请选择所在单位" style={{ width: '100%' }}
                                                 optionFilterProp="children"
                                                 notFoundContent="无法找到" searchPlaceholder="输入关键词"/>
        </FormItem>

        <FormItem
          { ...formItemLayout }
          label="手机号码："
          hasFeedback
        >
          <Input {...phoneProps} placeholder="请输入手机号码"/>
        </FormItem>

        <FormItem
          label="邮箱："
          { ...formItemLayout }
          hasFeedback
        >
          <EmailSelect { ...emailProps } />
        </FormItem>

        <Row type="flex" justify="center">
          <Col>
            { registerBtn }
          </Col>
        </Row>

      </Form>
    );
  }
}
