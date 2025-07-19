import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import './FeedbackViewer.css'; // Optional: for styling

function FeedbackViewer() {
  const { id } = useParams();
  const navigate = useNavigate();
   const location = useLocation();
  const appid = location.state?.appid;
  const [feedbacks, setFeedbacks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/customerfeedback/${id}`)
      .then(res => {
        setFeedbacks(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Failed to fetch feedback', err);
        setLoading(false);
      });
  }, [id]);

  if (loading) return <div className="container">Loading...</div>;

  return (
    <div className="container" style={{ maxWidth: '800px', margin: '0 auto' }}>
      <button
        onClick={() => navigate('/dashboard')}
        style={{ marginBottom: '20px' }}
      >
        ← Back to Dashboard
      </button>

      <h2>Customer Feedback for Application ID: {appid}</h2>

      {feedbacks.length === 0 ? (
        <p>No feedback available for this application.</p>
      ) : (
        <ul style={{ padding: 0, listStyle: 'none' }}>
          {feedbacks.map((fb: any, index: number) => (
            <li
              key={index}
              style={{
                border: '1px solid #ccc',
                padding: '10px',
                borderRadius: '6px',
                marginBottom: '10px'
              }}
            >
              <div style={{ fontSize: '14px', color: '#666' }}>
                {new Date(fb.createddate).toLocaleString()}
              </div>
              <div style={{ marginTop: '6px', fontSize: '16px' }}>
                {fb.feedback}
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default FeedbackViewer;
