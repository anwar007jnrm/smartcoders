import { Container, Paper, Typography } from '@mui/material'
import React from 'react'
import Steps from '../components/Steps';
import formJson from '../formEelement.json';

const REGRISTRATION_STEPS = formJson.pages.map((step: any) => ({
  name: step.title,
  Component: () => (
    <Typography variant="h6" sx={{ mt: 2, mb: 2 }}>
      {step.description}
    </Typography>
  ),
}));


const Register = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" gutterBottom>
          Setup Your Account
        </Typography>
        <Typography variant="body1">
            <Steps stepsConfig={REGRISTRATION_STEPS} formJson={formJson} />
        </Typography>
      </Paper>
    </Container>
  )
}

export default Register
