/* eslint-disable react/prop-types */

import { Button, StepLabel, TextField } from "@mui/material";
import Box from "@mui/material/Box";
import Step from "@mui/material/Step";
import Stepper from "@mui/material/Stepper";
import { use, useEffect, useRef, useState } from "react";
import Eelement from "./Eelement";

type StepConfig = {
  name: string;
  Component: React.FC;
};

interface StepsProps {
  stepsConfig: StepConfig[];
  formJson: any; // Assuming formJson is passed as a prop
}

const Steps: React.FC<StepsProps> = ({ stepsConfig, formJson }) => {
  const [currentStep, setCurrentStep] = useState(1);
  const [isComplete, setIsComplete] = useState(false);
  console.log(stepsConfig.length, "stepsConfig.length");

  //Writtting logic for dynamic element creation
  const [elements, setElements] = useState(
    formJson?.pages && formJson.pages.length > 0 ? formJson.pages[0] : null
  );
  const { fields } = elements ?? {}

  useEffect(() => {
    console.log("currentStep", currentStep);
    setElements(formJson.pages[currentStep - 1]);
    console.log("elements", elements);
  }, [currentStep, formJson]);



  const [margins, setMargins] = useState({
    marginLeft: 0,
    marginRight: 0,
  });

  if (!stepsConfig.length) {
    return <></>;
  }

  const handleBack = () => {
    setCurrentStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleNext = () => {
    setCurrentStep((prevStep) => {
      if (prevStep === stepsConfig.length) {
        setIsComplete(true);
        return prevStep;
      } else {
        return prevStep + 1;
      }
    });
  };

  const ActiveComponent = stepsConfig[currentStep - 1]?.Component;

  return (
    <Box sx={{ width: '100%' }}>
      <Stepper activeStep={currentStep - 1}>
        {stepsConfig.map((step, index) => {
          const stepProps: { completed?: boolean } = {};
          const labelProps: {
            optional?: React.ReactNode;
          } = {};

          return (
            <Step key={step.name} {...stepProps}>
              <StepLabel {...labelProps}>{step.name}</StepLabel>
            </Step>
          );
        })}
      </Stepper>

      <ActiveComponent />

      {/* Render dynamic fields using MUI TextField */}
      <Box sx={{ mt: 3 }}>
        {fields &&
          fields.map((field: any, idx: number) => (
            <Eelement key={idx} field={field} />
          ))}
      </Box>

      <Box justifyContent="flex-end" sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
        <Button
          color="inherit"
          variant="outlined"    
          disabled={(currentStep - 1) === 0}
          onClick={handleBack}
          sx={{ mr: 1, fontWeight: 700, color: "black" }}
        >
          Back
        </Button>

        {/* {!isComplete && ( */}
          <Button className="btn" sx={{bgcolor: "black", fontWeight: 700, color: "white"}} variant="contained" onClick={handleNext}>
            {currentStep >= stepsConfig.length ? "Finish" : "Next"}
          </Button>
        {/* )}  */}
      </Box>



    </Box>
  );
};

export default Steps;