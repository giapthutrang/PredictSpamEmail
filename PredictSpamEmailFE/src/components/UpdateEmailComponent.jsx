import React, { Component } from "react";
import EmailService from "../services/EmailService";

class UpdateEmailComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      id: this.props.match.params.id,
      description: "",
      subject: "",
      time: "",
    };
    this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
    this.changeSubjectHandler = this.changeSubjectHandler.bind(this);
  }

  componentDidMount() {
    EmailService.getEmailById(this.state.id).then((res) => {
      let email = res.data;
      this.setState({
        description: email.description,
        subject: email.lastName,
        time: email.time,
      });
    });
  }

  updateEmail = (e) => {
    e.preventDefault();
    let email = {
      description: this.state.description,
      subject: this.state.subject,
      time: this.state.time,
    };
    console.log("email => " + JSON.stringify(email));
    console.log("id => " + JSON.stringify(this.state.id));
    EmailService.updateEmail(email, this.state.id).then((res) => {
      this.props.history.push("/emails");
    });
  };

  changeDescriptionHandler = (event) => {
    this.setState({ description: event.target.value });
  };

  changeSubjectHandler = (event) => {
    this.setState({ subject: event.target.value });
  };

  cancel() {
    this.props.history.push("/emails");
  }

  render() {
    return (
      <div>
        <br></br>
        <div className="container">
          <div className="row">
            <div className="card col-md-6 offset-md-3 offset-md-3">
              <h3 className="text-center">Update Email</h3>
              <div className="card-body">
                <form>
                  <div className="form-group">
                    <label> Description: </label>
                    <input
                      placeholder="Description"
                      name="description"
                      className="form-control"
                      value={this.state.description}
                      onChange={this.changeDescriptionHandler}
                    />
                  </div>
                  <div className="form-group">
                    <label> Subject: </label>
                    <input
                      placeholder="Last Name"
                      name="subject"
                      className="form-control"
                      value={this.state.subject}
                      onChange={this.changeSubjectHandler}
                    />
                  </div>
                  <div className="form-group">
                    <label> Time: </label>
                    <input
                      placeholder="Local Time"
                      name="time"
                      className="form-control"
                      value={this.state.time}
                    />
                  </div>

                  <button
                    className="btn btn-success"
                    onClick={this.updateEmail}
                  >
                    Send
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={this.cancel.bind(this)}
                    style={{ marginLeft: "10px" }}
                  >
                    Cancel
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default UpdateEmailComponent;
