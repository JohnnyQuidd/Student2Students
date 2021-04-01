import './App.css'
import Welcome from './Components/Welcome/Welcome'
import Dashboard from './Components/Dashboard/Dashboard'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import NotFound from './Components/Error/NotFound';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact component={Welcome} />
          <Route path="/home" component={Dashboard} />
          <Route component={NotFound} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
