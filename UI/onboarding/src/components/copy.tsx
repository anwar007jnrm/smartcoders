/* eslint-disable react/prop-types */

import Box from "@mui/material/Box";
import { useEffect, useRef, useState } from "react";

type StepConfig = {
  name: string;
  Component: React.FC;
};

interface StepsProps {
  stepsConfig: StepConfig[];
}

const Steps: React.FC<StepsProps> = ({ stepsConfig }) => {
  const [currentStep, setCurrentStep] = useState(1);
  const [isComplete, setIsComplete] = useState(false);
  const [margins, setMargins] = useState({
    marginLeft: 0,
    marginRight: 0,
  });
  const stepRef = useRef<Array<HTMLDivElement | null>>([]);

  useEffect(() => {
    setMargins({
      marginLeft: (stepRef.current[0]?.offsetWidth ?? 0) / 2,
      marginRight: (stepRef.current[stepsConfig.length - 1]?.offsetWidth ?? 0) / 2,
    });
  }, [stepRef, stepsConfig.length]);

  if (!stepsConfig.length) {
    return <></>;
  }

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

  const calculateProgressBarWidth = () => {
    return ((currentStep - 1) / (stepsConfig.length - 1)) * 100;
  };

  const ActiveComponent = stepsConfig[currentStep - 1]?.Component;

  return (
    <Box sx={{ width: '100%' }}>
      <div className="stepper">
        {stepsConfig.map((step, index) => {
          return (
            <div
              key={step.name}
              ref={el => { stepRef.current[index] = el; }}
              className={`step ${currentStep > index + 1 || isComplete ? "complete" : ""
                } ${currentStep === index + 1 ? "active" : ""} `}
            >
              <div className="step-number">
                {currentStep > index + 1 || isComplete ? (
                  <span>&#10003;</span>
                ) : (
                  index + 1
                )}
              </div>
              <div className="step-name">{step.name}</div>
            </div>
          );
        })}

        <div
          className="progress-bar"
          style={{
            width: `calc(100%-${margins.marginLeft + margins.marginRight}px)`,
            marginLeft: margins.marginLeft,
            marginRight: margins.marginRight,
          }}
        >
          <div
            className="progress"
            style={{ width: `${calculateProgressBarWidth()}%` }}
          ></div>
        </div>
      </div>

      <ActiveComponent />

      {!isComplete && (
        <button className="btn" onClick={handleNext}>
          {currentStep === stepsConfig.length ? "Finish" : "Next"}
        </button>
      )}

    </Box>
  );
};

export default Steps;