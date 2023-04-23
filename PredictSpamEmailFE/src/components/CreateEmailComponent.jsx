import React, { Component } from 'react'
import EmailService from '../services/EmailService';

class CreateEmailComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            description: '',
            subject: '',
            time: ''
        }
        this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
        this.changeSubjectHandler = this.changeSubjectHandler.bind(this);
        this.saveOrUpdateEmail = this.saveOrUpdateEmail.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            EmailService.getEmailById(this.state.id).then( (res) =>{
                let email = res.data;
                this.setState({description: email.description,
                    subject: email.subject,
                    time : email.time
                });
            });
        }        
    }
    saveOrUpdateEmail = (e) => {
        e.preventDefault();
        let email = {description: this.state.description, subject: this.state.subject, time: this.state.time};
        console.log('email => ' + JSON.stringify(email));

        // step 5
        if(this.state.id === '_add'){
            EmailService.createEmail(email).then(res =>{
                this.props.history.push('/emails');
            });
        }else{
            EmailService.updateEmail(email, this.state.id).then( res => {
                this.props.history.push('/emails');
            });
        }
    }
    
    changeDescriptionHandler= (event) => {
        this.setState({description: event.target.value});
    }

    changeSubjectHandler= (event) => {
        this.setState({subject: event.target.value});
    }

    cancel(){
        this.props.history.push('/employees');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Send Email</h3>
        }else{
            return <h3 className="text-center">Update Email</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                    <div className = "form-group">
                                            <label> Subject: </label>
                                            <input placeholder="Subject" name="subject" className="form-control" 
                                                value={this.state.subject} onChange={this.changeSubjectHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Description: </label>
                                            <textarea placeholder="description" name="description" className="form-control" rows={10}
                                                value={this.state.description} onChange={this.changeDescriptionHandler}/>
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateEmail}>Send</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default CreateEmailComponent
