import axios from 'axios';
import { verify } from 'crypto';

const BASE_URL = 'http://localhost:8080/api';

export interface CreateApplicationPayload {
  status: string;
  salutation: string;
  firstname: string;
  lastname: string;
  mobilenumber: string;
  email: string;
  address: string;
  postalcode: string;
  formdata: string;
  currentpage: number;
  journeytype?: string;
  appid?: string;
}

interface ApplicationResponse {
  status?: string;
  salutation?: string;
  firstname?: string;
  lastname?: string;
  mobilenumber?: string;
  email?: string;
  address?: string;
  postalcode?: string;
  formdata?: string;
  currentpage?: number;
  journeytype?: string;
  appid?: string;
}
export const getApplication = async (id: string) => {
  try {
    const response = await axios.get(`${BASE_URL}/applications/${id}`);
    return response.data;
  } catch (error: any) {
    if (error.response) {
      console.log('Server Error', error.response.data?.message);
    } else if (error.request) {
      console.log('No response from server');
    } else {
      console.log('Unknown error', error.message);
    }
  }
};

export const createApplication = async (payload: CreateApplicationPayload) => {
  try {
    const response = await axios.post(`${BASE_URL}/applications`, payload);
    return response.data;
  } catch (error: any) {
    if (error.response) {
      console.log('Server Error', error.response.data?.message);
    } else if (error.request) {
      console.log('No response from server');
    } else {
      console.log('Unknown error', error.message);
    }
  }
};

export const updateApplication = async (id: string, currentpage: number, payload: Partial<CreateApplicationPayload>) => {
  try {
    const response = await axios.get<ApplicationResponse>(`${BASE_URL}/applications/${id}`);
    let responseData = response.data || {};
    let formData = JSON.parse(responseData.formdata || '{}');
    responseData.currentpage = currentpage;
    formData = { ...formData, ...payload };
    responseData = { ...responseData, formdata: JSON.stringify(formData) };
    const updateResponse = await axios.put(`${BASE_URL}/applications`, responseData);
    console.log('Update Response:', updateResponse.data);
    return updateResponse.data;

  } catch (error: any) {
    if (error.response) {
      console.log('Server Error', error.response.data?.message);
    } else if (error.request) {
      console.log('No response from server');
    } else {
      console.log('Unknown error', error.message);
    }
  }
};

export const resumeApplication = async (token: string) => {
  try {
    const response = await axios.get(`${BASE_URL}/applications/resume-journey?token=${token}`);
    return response.data;
  } catch (error: any) {
    if (error.response) {
      console.log('Server Error', error.response.data?.message);
    } else if (error.request) {
      console.log('No response from server');
    } else {
      console.log('Unknown error', error.message);
    }
  }
}

export const verifyOtp = async (id: string, otp: string) => {
  try {
    const response = await axios.post(`${BASE_URL}/otp/verify`, { id, otp });
    return response.data;
  } catch (error: any) {
    if (error.response) {
      console.log('Server Error', error.response.data?.message);
    } else if (error.request) {
      console.log('No response from server');
    } else {
      console.log('Unknown error', error.message);
    }
  }
}