import { Container, Paper, Typography } from '@mui/material'
import React from 'react'
import Steps from '../components/Steps';
import formJson from '../formEelement.json';
import { useSelector } from 'react-redux';
import { RootState } from '../store';

const REGRISTRATION_STEPS = formJson.pages.map((step: any) => ({
  name: step.title,
  Component: () => (
    <Typography variant="h6" sx={{ mt: 2, mb: 2 }}>
      {step.description}
    </Typography>
  ),
}));

const Register = () => {
  const application = useSelector((state: RootState) => state.application);
  
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" gutterBottom>
          Setup Your Account
        </Typography>
        <Typography component="div" variant="body1">
            <Steps stepsConfig={REGRISTRATION_STEPS} formJson={formJson} />
        </Typography>
        {/* Display Redux application data for confirmation/debugging */}
        <Typography variant="body2" sx={{ mt: 2 }}>
          <strong>Application ID:</strong> {application.id}<br/>
          <strong>App ID:</strong> {application.appid}<br/>
          <strong>Journey Type:</strong> {application.journeytype}
        </Typography>
      </Paper>
    </Container>
  )
}

export default Register
