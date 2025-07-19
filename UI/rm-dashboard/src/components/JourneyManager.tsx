import { useEffect, useState } from 'react';
import { getAllJourneys, addJourney, updateJourney, deleteJourney } from '../services/apiService';

function JourneyManager() {
  const [journeys, setJourneys] = useState<any[]>([]);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [form, setForm] = useState({ journeytype: '', version: 'v1', templatedata: '' });

  useEffect(() => {
    fetchJourneys();
  }, []);

  const fetchJourneys = async () => {
    const data = await getAllJourneys();
    setJourneys(data);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editingId !== null) {
      await updateJourney(editingId, form);
    } else {
      await addJourney(form);
    }
    setForm({ journeytype: '', version: 'v1', templatedata: '' });
    setEditingId(null);
    fetchJourneys();
  };

  const handleEdit = (journey: any) => {
    setEditingId(journey.id);
    setForm({
      journeytype: journey.journeytype,
      version: journey.version || 'v1',
      templatedata: journey.templatedata,
    });
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this journey?')) {
      await deleteJourney(id);
      fetchJourneys();
    }
  };

  return (
    <div className="container" style={{ maxWidth: '1100px', margin: '0 auto' }}>
      <h2>Journey Manager</h2>

      {/* Form for Add/Edit */}
      <form onSubmit={handleSubmit} style={{ marginBottom: '20px' }}>
        <label>
          Journey Type:
          <input
            type="text"
            required
            value={form.journeytype}
            onChange={(e) => setForm({ ...form, journeytype: e.target.value })}
          />
        </label>
        <label>
          Version:
          <input
            type="text"
            value={form.version}
            onChange={(e) => setForm({ ...form, version: e.target.value })}
          />
        </label>
        <label>
          Template JSON:
          <textarea
            required
            rows={5}
            value={form.templatedata}
            onChange={(e) => setForm({ ...form, templatedata: e.target.value })}
          />
        </label>
        <button type="submit">{editingId !== null ? 'Update' : 'Add'} Journey</button>
      </form>

      {/* Table of Existing Journeys */}
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Journey Type</th>
            <th>Version</th>
            <th>Created</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {journeys.map(j => (
            <tr key={j.id}>
              <td>{j.id}</td>
              <td>{j.journeytype}</td>
              <td>{j.version}</td>
              <td>{j.createddate?.replace('T', ' ').slice(0, 16)}</td>
              <td>
                <button onClick={() => handleEdit(j)}>Edit</button>
                <button onClick={() => handleDelete(j.id)} style={{ marginLeft: '8px' }}>Delete</button>
              </td>
            </tr>
          ))}
          {journeys.length === 0 && (
            <tr>
              <td colSpan={5} style={{ textAlign: 'center', padding: '10px' }}>No journeys found.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default JourneyManager;
