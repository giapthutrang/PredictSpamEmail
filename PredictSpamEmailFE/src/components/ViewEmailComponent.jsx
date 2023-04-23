import React, { Component } from 'react'
import EmailService from '../services/EmailService'

class ViewEmailComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            email: {}
        }
    }

    componentDidMount(){
        EmailService.getEmailById(this.state.id).then( res => {
            this.setState({email: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Email Details</h3>
                    <div className = "card-body">
                    <div className = "row">
                            <label> Subject: </label>
                            <div> { this.state.email.subject }</div>
                        </div>
                        
                        <div className = "row">
                            <label> Description: </label>
                            <div> { this.state.email.description }</div>
                        </div>
                    
                        <div className = "row">
                            <label> Time: </label>
                            <div> { this.state.email.time }</div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default ViewEmailComponent
