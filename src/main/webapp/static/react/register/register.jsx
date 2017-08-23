import 'antd/lib/index.css';
import '../../css/register.css';
import React from 'react';
import ReactDOM from 'react-dom';
import Ajax from '../ajax.jsx';
import { Steps, Form, Row } from 'antd';
import AjaxSelect from '../ajax-select.jsx';
import LoginModal from './login-modal.jsx';
import BindWeixin from './bind-weixin.jsx';
import RegisterForm from './register-form.jsx';
import Logout from './logout.jsx';
import Pay from './pay.jsx';
const createForm = Form.create;
const Step = Steps.Step;

const MyRegisterForm = createForm()(RegisterForm);
const MyLoginModal = createForm()(LoginModal);

class RegisterStep extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      step: 0
    }
    this.adjustStep();
  }

  adjustStep() {
    Ajax.get({
      url: "/user/registerStep.json",
      data: {
        step: this.state.step
      }
    }).then(jsonWrapper => {
      this.setState({step: jsonWrapper.result});
    });
  }

  setStep = (step) => {
    console.log(`set step : ${step} `)
    if (typeof step != 'undefined') {
      this.setState({step});
    } else {
      this.adjustStep();
    }
  }

  render() {
    let form = null;
    switch (this.state.step) {
      case 0:
        form = (<div><MyLoginModal setStep={ this.setStep }/><MyRegisterForm closeRegister={ false }
                                                                             setStep={ this.setStep }/></div>)
        break;
      case 1:
        form = <div><Logout setStep={ this.setStep }/> <Pay /></div>;
        break;
      case 2:
        form = <div><Logout setStep={ this.setStep }/> <BindWeixin /></div>;
        break;
      case -1:
        form = (
          <div><MyLoginModal setStep={ this.setStep }/><MyRegisterForm closeRegister={ true } setStep={ this.setStep }/>
          </div>)
        break;
    }
    return (
      <div className="register">
        <br/>
        <div style={{ padding: '0 30px' }}>
          <Steps current={this.state.step}>
            <Step title="注册" icon="cloud"/>
            <Step title="绑定" icon="apple"/>
          </Steps>
        </div>
        { form }
      </div>
    );
  }
}

export default RegisterStep;

