import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListEmailComponent from './components/ListEmailComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CreateEmailComponent from './components/CreateEmailComponent';
import UpdateEmailComponent from './components/UpdateEmailComponent';
import ViewEmailComponent from './components/ViewEmailComponent';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListEmailComponent}></Route>
                          <Route path = "/emails" component = {ListEmailComponent}></Route>
                          <Route path = "/add-email/:id" component = {CreateEmailComponent}></Route>
                          <Route path = "/view-email/:id" component = {ViewEmailComponent}></Route>
                          {/* <Route path = "/update-employee/:id" component = {UpdateEmployeeComponent}></Route> */}
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;
