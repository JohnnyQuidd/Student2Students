import './App.css'
import Welcome from './Components/Welcome/Welcome'
import StudentDashboard from './Components/Dashboard/StudentDashboard'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import NotFound from './Components/Error/NotFound';
import AdminDashboard from './Components/Dashboard/AdminDashboard';
import UniversityManagement from './Components/Dashboard/UniversityManagement';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact component={Welcome} />
          <Route path="/home" component={StudentDashboard} />
          <Route path="/admin" exact component={AdminDashboard} />
          <Route path="/admin/university" component={UniversityManagement} />
          <Route component={NotFound} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
