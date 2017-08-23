import React from 'react';
import ReactDOM from 'react-dom';
import Entry from './exam-entry.jsx';
import Exam from './exam.jsx';
import Score from './score.jsx';

export default class Checker extends React.Component {
  constructor() {
    super();
    let contentType = null;
    switch (global.code) {
      case 4:
        contentType = 'score'; // 已考
        break;
      default:
        contentType = 'entry';
    }
    this.state = {
      contentType,
    }
  }

  startExam() {
    this.setState({contentType: 'exam'});
  }

  render() {
    let content = null;
    if (this.state.contentType === 'entry') {
      content = <Entry handleClick={ this.startExam.bind(this) }/>
    } else if (this.state.contentType === 'exam') {
      content =
        <Exam questions={ global.questions } limit={ 30 * 60 * 1000 }
              userId={ global.user.id } examId={ global.exam.id } />;
    } else if (this.state.contentType === 'score') {
      content = <Score />
    }
    return (
      <div style={{ height: '100%' }}>
        {content}
      </div>
    );
  }
}
