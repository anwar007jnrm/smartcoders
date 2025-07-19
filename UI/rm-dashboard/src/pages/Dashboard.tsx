import { useEffect, useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClipboardList, faCommentDots } from '@fortawesome/free-solid-svg-icons'; // added

function Dashboard() {
  const navigate = useNavigate();
  const rmName = localStorage.getItem('rmName');
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  const [appIdSearch, setAppIdSearch] = useState('');
  const [firstNameSearch, setFirstNameSearch] = useState('');
  const [lastNameSearch, setLastNameSearch] = useState('');
  const [emailSearch, setEmailSearch] = useState('');
  const [phoneSearch, setPhoneSearch] = useState('');
  const [journeySearch, setJourneySearch] = useState('');

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/applications`)
      .then(res => {
        setApplications(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Error fetching applications', err);
        setLoading(false);
      });
  }, []);

  const handleAssign = async (id: number) => {
    try {
      await axios.put(`http://localhost:8080/api/applications/${id}/assign`, {
        assignedTo: rmName
      });

      await axios.post(`http://localhost:8080/api/rmaudit`, {
        rmid: rmName,
        applicationid: id,
        message: `Application assigned to ${rmName}`
      });

      setApplications(prev =>
        prev.map(app =>
          app.id === id ? { ...app, rmid: rmName } : app
        )
      );
    } catch (err) {
      console.error('Assign failed', err);
    }
  };

  const filtered = useMemo(() => {
    return applications
      .filter(app => {
        return (
          app.appid?.toLowerCase().includes(appIdSearch.toLowerCase()) &&
          app.firstname?.toLowerCase().includes(firstNameSearch.toLowerCase()) &&
          app.lastname?.toLowerCase().includes(lastNameSearch.toLowerCase()) &&
          app.email?.toLowerCase().includes(emailSearch.toLowerCase()) &&
          app.mobilenumber?.includes(phoneSearch) &&
          app.journeytype?.toLowerCase().includes(journeySearch.toLowerCase())
        );
      })
      .sort((a, b) => {
        const aMine = a.rmid === rmName ? -1 : 1;
        const bMine = b.rmid === rmName ? -1 : 1;
        return aMine - bMine;
      });
  }, [applications, appIdSearch, firstNameSearch, lastNameSearch, emailSearch, phoneSearch, journeySearch, rmName]);

  if (loading) return <div>Loading...</div>;

  return (
    <div className="container" style={{ maxWidth: '1400px', margin: '0 auto' }}>
      <h2 style={{ textAlign: 'center' }}>Welcome, {rmName}</h2>

      <div style={{ overflowX: 'auto' }}>
        <table style={{ width: '100%', borderCollapse: 'collapse', minWidth: '1100px' }}>
          <thead>
            <tr>
              <th>App ID</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Journey</th>
              <th>Status</th>
              <th>Assigned</th>
            </tr>
            <tr>
              <th><input type="text" value={appIdSearch} onChange={e => setAppIdSearch(e.target.value)} placeholder="Search App ID" style={searchBoxStyle} /></th>
              <th><input type="text" value={firstNameSearch} onChange={e => setFirstNameSearch(e.target.value)} placeholder="Search First" style={searchBoxStyle} /></th>
              <th><input type="text" value={lastNameSearch} onChange={e => setLastNameSearch(e.target.value)} placeholder="Search Last" style={searchBoxStyle} /></th>
              <th><input type="text" value={emailSearch} onChange={e => setEmailSearch(e.target.value)} placeholder="Search Email" style={searchBoxStyle} /></th>
              <th><input type="text" value={phoneSearch} onChange={e => setPhoneSearch(e.target.value)} placeholder="Search Phone" style={searchBoxStyle} /></th>
              <th><input type="text" value={journeySearch} onChange={e => setJourneySearch(e.target.value)} placeholder="Search Journey" style={searchBoxStyle} /></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {filtered.map(app => (
              <tr key={app.id}>
                <td>
                  {app.appid}
                  {app.rmid === rmName && (
                    <>
                      <button
                        onClick={() => navigate(`/application/${app.id}`)}
                        title="Add audit to application"
                        style={iconStyle}
                      >
                        <FontAwesomeIcon icon={faClipboardList} />
                      </button>
                      <button
                        onClick={() => navigate(`/feedback/${app.id}`,{
                          state: { appid: app.appid }
                        })}
                        title="View customer feedback"
                        style={iconStyle}
                      >
                        <FontAwesomeIcon icon={faCommentDots} />
                      </button>
                    </>
                  )}
                </td>
                <td>{app.firstname}</td>
                <td>{app.lastname}</td>
                <td>{app.email}</td>
                <td>{app.mobilenumber}</td>
                <td>{app.journeytype}</td>
                <td><span className={`status ${app.status}`}></span></td>
                <td>
                  {app.rmid === rmName ? (
                    <span>{app.rmid}</span>
                  ) : (
                    <button
                      onClick={() => handleAssign(app.id)}
                      style={assignBtnStyle}
                    >
                      Assign to Me
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

const searchBoxStyle: React.CSSProperties = {
  width: '100%',
  padding: '6px',
  border: '1px solid #ccc',
  borderRadius: '4px',
  fontSize: '12px'
};

const iconStyle: React.CSSProperties = {
  background: 'transparent',
  border: 'none',
  fontSize: '16px',
  cursor: 'pointer',
  color: '#0057b8',
  marginLeft: '6px'
};

const assignBtnStyle: React.CSSProperties = {
  backgroundColor: '#007a33',
  color: 'white',
  border: 'none',
  padding: '6px 10px',
  borderRadius: '4px',
  fontSize: '12px',
  cursor: 'pointer'
};

export default Dashboard;
