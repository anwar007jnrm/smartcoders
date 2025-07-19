import { useParams, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMicrophone } from '@fortawesome/free-solid-svg-icons';
import './ApplicationDetails.css';

function ApplicationDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  //const [application, setApplication] = useState<any>(null);
  const [auditLogs, setAuditLogs] = useState<any[]>([]);
  const [note, setNote] = useState('');
  const rmName = localStorage.getItem('rmId') || 'RM';

  useEffect(() => {
    // Fetch application data
    /*axios.get(`http://localhost:8080/api/applications/${id}`)
      .then(res => setApplication(res.data))
      .catch(err => console.error('Failed to fetch application', err));*/

    // Fetch audit logs separately
    axios.get(`http://localhost:8080/api/rmaudit/${id}`)
      .then(res => setAuditLogs(res.data))
      .catch(err => console.error('Failed to fetch audit logs', err));
  }, [id]);

  const handleAddLog = async () => {
    if (!note.trim()) return;

    try {
      const response = await axios.post(`http://localhost:8080/api/rmaudit`, {
        rmid: rmName,
        applicationid: id,
        message: note
      });

      // Prepend new log
      setAuditLogs(prev => [response.data, ...prev]);
      setNote('');
    } catch (error) {
      console.error('Failed to add log', error);
    }
  };

  const handleSpeech = () => {
    const SpeechRecognition =
      (window as any).webkitSpeechRecognition || (window as any).SpeechRecognition;
    if (!SpeechRecognition) {
      alert('Speech Recognition not supported in this browser.');
      return;
    }

    const recognition = new SpeechRecognition();
    recognition.lang = 'en-US';
    recognition.interimResults = false;
    recognition.maxAlternatives = 1;

    recognition.onresult = (event: any) => {
      const transcript = event.results[0][0].transcript;
      setNote(prev => prev + ' ' + transcript);
    };

    recognition.onerror = (event: any) => {
      console.error('Speech recognition error', event.error);
      alert('Speech recognition error: ' + event.error);
    };

    recognition.start();
  };

  if (!auditLogs) return <div className="container">Loading...</div>;

  return (
    <div className="container" style={{ maxWidth: '1400px', margin: '0 auto' }}>
      {/* Back */}
      <div className="back-button">
        <button onClick={() => navigate('/dashboard')} aria-label="Back to Dashboard">
          ← Back to Dashboard
        </button>
      </div>

      <h2>Audit History</h2>

      {/* Audit List */}
      <div className="audit-history">
        <ul>
          {auditLogs.map((log, index) => (
            <li key={index}>
              <div className="audit-meta">
                {log.rmid}{' '}
                <span className="audit-date">
                  at {new Date(log.createddate).toLocaleString()}
                </span>
              </div>
              <div className="audit-note">{log.message}</div>
            </li>
          ))}
        </ul>
      </div>

      {/* Add Entry */}
      <div className="add-audit-section">
        <h3>Add New Audit Entry</h3>

        <div className="audit-input-wrapper">
          <textarea
            value={note}
            onChange={e => setNote(e.target.value)}
            placeholder="Enter audit log or use speech..."
            aria-label="Audit entry"
          />
          <button
            onClick={handleSpeech}
            title="Click to speak"
            aria-label="Voice input"
            className="speech-button"
          >
            <FontAwesomeIcon icon={faMicrophone} />
          </button>
        </div>

        <button onClick={handleAddLog} className="add-log-btn">
          Add Log
        </button>
      </div>
    </div>
  );
}

export default ApplicationDetails;
