import { Link } from 'react-router-dom';
import { useState } from 'react';
import data from '../data/mockApplications';

function ApplicationList() {
  const [applications, setApplications] = useState(data);
  const rmName = localStorage.getItem('rmName') || 'RM';

  const handleAssign = (id: number) => {
    const updated = applications.map(app =>
      app.id === id ? { ...app, assignedTo: rmName } : app
    );
    setApplications(updated);
  };

  return (
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Email</th>
          <th>Phone</th>
          <th>Journey</th>
          <th>Status</th>
          <th>Assigned To</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {applications.map(app => (
          <tr key={app.id}>
            <td>{app.firstName} {app.lastName}</td>
            <td>{app.email}</td>
            <td>{app.phone}</td>
            <td>{app.journey}</td>
            <td><span className={`status ${app.status}`}></span></td>
            <td>{app.assignedTo || 'Unassigned'}</td>
            <td>
              <Link to={`/application/${app.id}`} className="text-blue-600 underline">View</Link>
              <button onClick={() => handleAssign(app.id)} style={{ marginLeft: '10px' }}>
                Assign to Me
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ApplicationList;
