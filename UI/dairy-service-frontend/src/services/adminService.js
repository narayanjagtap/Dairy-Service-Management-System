import api from './api';

export const adminService = {
  // Get admin dashboard
  getDashboard: async () => {
    try {
      const response = await api.get('/admin/dashboard');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get all requests
  getAllRequests: async () => {
    try {
      const response = await api.get('/admin/requests');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get single request
  getRequest: async (id) => {
    try {
      const response = await api.get(`/admin/requests/${id}`);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Update request status
  updateRequestStatus: async (id, status) => {
    try {
      const response = await api.put(`/admin/request/${id}/status`, { status });
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get all bills
  getAllBills: async () => {
    try {
      const response = await api.get('/admin/bills');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get all bills (alias)
  getBills: async () => {
    try {
      const response = await api.get('/admin/bills');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get single bill
  getBill: async (id) => {
    try {
      const response = await api.get(`/admin/bills/${id}`);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Create bill
  createBill: async (billData) => {
    try {
      const response = await api.post('/admin/bill', billData);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Update bill
  updateBill: async (id, billData) => {
    try {
      const response = await api.put(`/admin/bill/${id}`, billData);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Mark bill as paid
  markBillAsPaid: async (id) => {
    try {
      const response = await api.put(`/admin/bill/${id}/paid`, { status: 'PAID' });
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Mark bill as unpaid
  markBillAsUnpaid: async (id) => {
    try {
      const response = await api.put(`/admin/bill/${id}/unpaid`, { status: 'UNPAID' });
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Delete bill
  deleteBill: async (id) => {
    try {
      const response = await api.delete(`/admin/bill/${id}`);
      return response.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get all customers
  getAllCustomers: async () => {
    try {
      const response = await api.get('/admin/customers');
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get single customer
  getCustomer: async (id) => {
    try {
      const response = await api.get(`/admin/customers/${id}`);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Update customer
  updateCustomer: async (id, customerData) => {
    try {
      const response = await api.put(`/admin/customers/${id}`, customerData);
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Delete customer
  deleteCustomer: async (id) => {
    try {
      const response = await api.delete(`/admin/customers/${id}`);
      return response.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Get reports
  getReports: async (filters) => {
    try {
      const response = await api.get('/admin/reports', { params: filters });
      return response.data.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },
};