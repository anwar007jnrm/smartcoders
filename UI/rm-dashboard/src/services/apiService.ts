import axios from 'axios';

const API_BASE = 'http://localhost:8080/api'; // Update this if your backend runs on a different base URL

// ---------------------------
// LOGIN
// ---------------------------
export const getAllJourneys = async () => {
  const res = await fetch('http://localhost:8080/api/journeys');
  return res.json();
};

export const addJourney = async (data: any) => {
  const res = await fetch('http://localhost:8080/api/journeys', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  return res.json();
};

export const updateJourney = async (id: number, data: any) => {
  const res = await fetch(`http://localhost:8080/api/journeys/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  return res.json();
};

export const deleteJourney = async (id: number) => {
  return fetch(`http://localhost:8080/api/journeys/${id}`, {
    method: 'DELETE',
  });
};


export const loginUser = async (rmName: string, password: string) => {
  const response = await axios.post(`${API_BASE}/login`, { rmName, password });
  return response.data; // expects { role: 'Admin' | 'RM', rmName: '...' }
};

// ---------------------------
// APPLICATIONS
// ---------------------------
export const fetchApplications = async () => {
  const response = await axios.get(`${API_BASE}/applications`);
  return response.data;
};

export const addAuditLog = async (applicationId: number, note: string, by: string) => {
  const response = await axios.post(`${API_BASE}/applications/${applicationId}/audit`, {
    note,
    by
  });
  return response.data;
};

export const assignApplication = async (applicationId: number, assignedTo: string) => {
  const response = await axios.post(`${API_BASE}/applications/${applicationId}/assign`, {
    assignedTo
  });
  return response.data;
};

// ---------------------------
// JOURNEYS
// ---------------------------
export const fetchJourneys = async () => {
  const response = await axios.get(`${API_BASE}/journeys`);
  return response.data;
};

export const createJourney = async (
  journeytype: string,
  version: string,
  templatedata: object
) => {
  const response = await axios.post(`${API_BASE}/journeys`, {
    journeytype,
    version,
    templatedata
  });
  return response.data;
};
