import React, { useState } from 'react';
import {
  Box,
  Button,
  MenuItem,
  TextField,
  Typography,
  InputAdornment,
  Container,
  Paper,
} from '@mui/material';
import { BrowserRouter as Router, Link as RouterLink, Routes, Route } from "react-router-dom";

// Example options
const salutationOptions = [
  { value: 'Mr', label: 'Mr' },
  { value: 'Mrs', label: 'Mrs' },
  { value: 'Ms', label: 'Ms' },
  { value: 'Dr', label: 'Dr' },
];

const countryCodeOptions = [
  { value: '+1', label: '+1 (USA)' },
  { value: '+44', label: '+44 (UK)' },
  { value: '+91', label: '+91 (India)' },
  // Add more as needed
];

const StartApplication: React.FC = () => {
  const [form, setForm] = useState({
    salutation: '',
    firstname: '',
    lastname: '',
    countryCode: '',
    mobilenumber: '',
    otp: '',
    email: '',
    address: '',
    postalcode: '',
  });

  const [otpSent, setOtpSent] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);

  // Simulate OTP send/verify
  const handleSendOtp = () => {
    if (form.mobilenumber && form.countryCode) {
      setOtpSent(true);
      // In real app, call backend to send OTP here
    }
  };

  const handleVerifyOtp = () => {
    if (form.otp === '123456') { // Simulate OTP check
      setOtpVerified(true);
    } else {
      setOtpVerified(false);
      alert('Invalid OTP');
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 3, borderRadius: 3 }}>
        <Box component="form" sx={{ mt: 2, maxWidth: 700, mx: 'auto' }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Personal Details
          </Typography>
          <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
            <TextField
              select
              label="Salutation"
              name="salutation"
              value={form.salutation}
              onChange={handleChange}
              required
              sx={{ minWidth: 120 }}
            >
              {salutationOptions.map((option) => (
                <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
              ))}
            </TextField>
            <TextField
              label="First Name"
              name="firstname"
              value={form.firstname}
              onChange={handleChange}
              required
              sx={{ flex: 1 }}
            />
            <TextField
              label="Last Name"
              name="lastname"
              value={form.lastname}
              onChange={handleChange}
              required
              sx={{ flex: 1 }}
            />
          </Box>
          <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
            <TextField
              select
              label="Country Code"
              name="countryCode"
              value={form.countryCode}
              onChange={handleChange}
              required
              sx={{ minWidth: 150 }}
            >
              {countryCodeOptions.map((option) => (
                <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
              ))}
            </TextField>
            <TextField
              label="Mobile Number"
              name="mobilenumber"
              value={form.mobilenumber}
              onChange={handleChange}
              required
              sx={{ flex: 1 }}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    {form.countryCode}
                  </InputAdornment>
                ),
              }}
              disabled={!form.countryCode}
            />
            <Button
              variant="contained"
              color="primary"
              sx={{ minWidth: 120 }}
              onClick={handleSendOtp}
              disabled={otpSent || !form.mobilenumber || !form.countryCode}
            >
              {otpSent ? 'OTP Sent' : 'Send OTP'}
            </Button>
          </Box>
          {otpSent && (
            <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
              <TextField
                label="Enter OTP"
                name="otp"
                value={form.otp}
                onChange={handleChange}
                type="password"
                required
                sx={{ flex: 1 }}
                disabled={otpVerified}
              />
              <Button
                variant="contained"
                color={otpVerified ? "success" : "primary"}
                onClick={handleVerifyOtp}
                disabled={otpVerified}
                sx={{ minWidth: 120 }}
              >
                {otpVerified ? 'Verified' : 'Verify OTP'}
              </Button>
            </Box>
          )}
          <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
            <TextField
              label="Email"
              name="email"
              value={form.email}
              onChange={handleChange}
              required
              sx={{ flex: 1 }}
              type="email"
            />
            <TextField
              label="Postal Code"
              name="postalcode"
              value={form.postalcode}
              onChange={handleChange}
              required
              sx={{ flex: 1 }}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Address"
              name="address"
              value={form.address}
              onChange={handleChange}
              required
              fullWidth
              multiline
              minRows={2}
            />
          </Box>
          <Box sx={{ mt: 3, display: 'flex', justifyContent: 'flex-end' }}>
            <Button
              variant="contained"
              disabled={!otpVerified}
              sx={{bgcolor: "black", fontWeight: 700, color: "white"}}
              component={RouterLink}
              to="/setup-account"
            >
              Submit
            </Button>
          </Box>
        </Box>
      </Paper>
    </Container>
  );
};

export default StartApplication;