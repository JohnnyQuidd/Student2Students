import './App.css'
import Welcome from './Components/Welcome/Welcome'
import UserDashboard from './Components/Dashboard/UserDashboard'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import NotFound from './Components/Error/NotFound';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact component={Welcome} />
          <Route path="/home" component={UserDashboard} />
          <Route component={NotFound} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
