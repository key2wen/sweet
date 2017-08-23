import 'antd/style/index.less';
import '../../less/exam.less';
import React from 'react';
import ReactDOM from 'react-dom';
import { Modal, Carousel, Button, Icon, Alert, Row, Col, message, Spin } from 'antd';
import Question from './question.jsx';
import Score from './score.jsx';
import Timer from '../timer.jsx';
import Emitter from '../emitter.jsx';
import Ajax from '../ajax.jsx';

export default class Exam extends React.Component {

  static propTypes = {
    questions: React.PropTypes.array.isRequired,
    limit: React.PropTypes.number.isRequired,
    userId: React.PropTypes.number.isRequired,
    examId: React.PropTypes.number.isRequired,
  }

  state = {
    currQuestion: 1,
    answers: new Array(this.props.questions.length),
    modalVisible: false,
    remaining: this.props.limit,
    loading: false
  }

  constructor(props) {
    super(props);
    const start = Date.now();
  }

  pre() {
    let pre = this.state.currQuestion - 1;
    if (pre === 0) {
      pre = this.props.questions.length;
    }
    this.state.currQuestion = pre;
    this.setState(this.state);
  }

  next() {
    let next = (this.state.currQuestion) % this.props.questions.length + 1;
    this.state.currQuestion = next;
    this.setState(this.state);
  }

  handleBeforeChange(from, to) {
    // 阻止选项的onClick事件
    Emitter.dispatch('slideBeforeChange');
  }

  handleAfterChange(current) {
    Emitter.dispatch('slideAfterChange');
    this.setState({currQuestion: current + 1});
  }

  handleSelect(index, option) {
    this.state.answers[index] = option;
  }

  showModal() {
    this.setState({modalVisible: true});
  }

  handleSubmit = () => {
    // 时间到也会触发提交
    if (this.state.loading) {
      return;
    }
    let answers = {};
    this.props.questions.forEach((e, i) => {
      let key = e.id.toString();
      let answer = this.state.answers[i];
      if (typeof answer === 'undefined') {
        answer = null;
      }
      answers[key] = answer;
    });

    let param = {
      userId: this.props.userId,
      examId: this.props.examId,
      answers
    };

    let paramJson = JSON.stringify(param);
    console.log(paramJson);

    this.setState({ loading: true });

    Ajax.post({
      url: '/score/submit',
      async: true,
      data: {
        param: paramJson
      }
    }).then((jsonWrapper) => {
      if (jsonWrapper && jsonWrapper.status.status_code === 0) {
        message.success("交卷成功");
        setTimeout(() => {
          window.location = '/examination/online';
        }, 500);
      } else {
        message.error("交卷失败");
        this.setState({ loading: false });
      }
    });

  }

  handleCancel = () => {
    this.setState({modalVisible: false});
  }

  render() {
    const questions = this.props.questions.map((q, i) => {
      return (<div key={ `q_" + ${ i }` }><Question qid={ q.id } index={ i } description={ q.description }
                                                    optionA={ q.optionA }
                                                    optionB={ q.optionB } optionC={ q.optionC } optionD={ q.optionD }
                                                    handleSelect={this.handleSelect.bind(this) }/>
      </div>)
    });

    let unanswered = '';
    for (let i = 0; i < this.state.answers.length; i++) {
      if (typeof this.state.answers[i] === 'undefined') {
        if (unanswered.length > 0) {
          unanswered += ', ';
        }
        unanswered += (i + 1);
      }
    }

    return (
      <div style={{ height: '100%', width: '100%', backgroundColor: '#f9f9f9' }}>
        <div className="exam_header">
          <div className="exam_header_tips">{
            <span>剩余时间:<Timer limit={ this.props.limit }
                              remind={ this.handleSubmit }/></span> }</div>
          <div className="exam_header_progress">进度: <span
            className="exam_header_progress_curr">{this.state.currQuestion}</span>&nbsp;
            /&nbsp;{this.props.questions.length}
          </div>
        </div>
        <Carousel dots={ false } beforeChange={ this.handleBeforeChange.bind(this) }
                  afterChange={ this.handleAfterChange.bind(this) }>
          { questions }
        </Carousel>

        <div className="exam_button">
          <Button type="ghost" size="large" onClick={ this.showModal.bind(this) }>交卷</Button>
        </div>

        <Modal width="90%" style={ { maxWidth: '700px' } }
               title="交卷"
               visible={this.state.modalVisible}
               onOk={ this.handleSubmit }
               footer={[
                <Button key="back" type="ghost" size="large" onClick={this.handleCancel}
                style={{ display: this.state.loading ? 'none' : 'inline' }}>返 回</Button>,
                <Button key="submit" type="primary" size="large" loading={this.state.loading}
                        onClick={ this.handleSubmit }>
                  提 交
                </Button>
                ]}
        >

          <div>
            <Spin spining={ this.state.loading }> <span
              style={{ fontSize: '18px' }}>{ unanswered.length > 0 ?
              <font color="red"> {`您还有未填写答案的题目: ${ unanswered } `}</font> :
              '您已回答了所有问题! ' } <br/> 确认要交卷吗?
              </span> </Spin>
          </div>

        </Modal>


      </div>
    )
  }
}

