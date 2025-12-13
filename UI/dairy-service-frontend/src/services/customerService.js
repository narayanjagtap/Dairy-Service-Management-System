import api from './api';

export const customerService = {
  // Get customer dashboard
  getDashboard: async () => {
    try {
      const response = await api.get('/customer/dashboard');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get all requests for customer
  getRequests: async () => {
    try {
      const response = await api.get('/customer/requests');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get single request
  getRequest: async (id) => {
    try {
      const response = await api.get(`/customer/requests/${id}`);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Create new request
  createRequest: async (requestData) => {
    try {
      const response = await api.post('/customer/request', requestData);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Update request
  updateRequest: async (id, requestData) => {
    try {
      const response = await api.put(`/customer/requests/${id}`, requestData);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Delete request
  deleteRequest: async (id) => {
    try {
      const response = await api.delete(`/customer/requests/${id}`);
      return response.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get customer bills
  getBills: async () => {
    try {
      const response = await api.get('/customer/bills');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get single bill
  getBill: async (id) => {
    try {
      const response = await api.get(`/customer/bills/${id}`);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Pay bill
  payBill: async (id, paymentData) => {
    try {
      const response = await api.post(`/customer/bills/${id}/pay`, paymentData);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get customer profile
  getProfile: async () => {
    try {
      const response = await api.get('/customer/profile');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Update customer profile
  updateProfile: async (profileData) => {
    try {
      const response = await api.put('/customer/profile', profileData);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  getOutstanding: async () => {
  try {
    const response = await api.get('/customer/outstanding');
    return response.data.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
},
};