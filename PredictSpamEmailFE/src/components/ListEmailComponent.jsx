import React, { Component } from 'react'
import EmailService from '../services/EmailService'

class ListEmailComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                emails: []
        }
        this.addEmail = this.addEmail.bind(this);
        this.editEmail = this.editEmail.bind(this);
        this.deleteEmail = this.deleteEmail.bind(this);
    }

    deleteEmail(id){
        EmailService.deleteEmail(id).then( res => {
            this.setState({emails: this.state.emails.filter(email => email.id !== id)});
        });
    }
    viewEmail(id){
        this.props.history.push(`/view-email/${id}`);
    }
    editEmail(id){
        this.props.history.push(`/add-email/${id}`);
    }

    componentDidMount(){
        EmailService.getEmails().then((res) => {
            this.setState({ emails: res.data});
        });
    }

    addEmail(){
        this.props.history.push('/add-email/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Emails List</h2>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addEmail}> Add Email</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Subject</th>
                                    <th> Description</th>
                                    <th> Time</th>
                                    <th> Spam</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.emails.map(
                                        email => 
                                        <tr key = {email.id}>
                                             <td> {email.subject}</td>
                                             <td> {email.description} </td>   
                                             <td> {email.time}</td>
                                             <td> {email.spam ? "Thư rác" : "Bình thường"}</td>
                                             <td>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteEmail(email.id)} className="btn btn-danger">Xóa </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewEmail(email.id)} className="btn btn-info">Xem </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListEmailComponent
