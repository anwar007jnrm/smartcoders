
INSERT INTO applications (id, appid, journeytype, status, rmid, salutation, firstname, lastname, countryCode, mobilenumber, email, address, postalcode, formdata, currentpage, createddate, updateddate)
VALUES (
           1, 'LOA-1001', 'Loan', 'inprogress', 'anwar', 'Mr', 'Anwar', 'Sk', '+91','9876543210',
           'anwar@example.com', '123 MG Road, Bangalore', '560001',
           '{"loanAmount": 500000, "tenure": 36}', 1, '2025-07-15 10:00:00', '2025-07-15 10:00:00'
       );

INSERT INTO applications (id, appid, journeytype, status, rmid, salutation, firstname, lastname, countryCode, mobilenumber, email, address, postalcode, formdata, currentpage, createddate, updateddate)
VALUES (
           2, 'LOA-1003', 'Loan', 'inprogress', 'rahul', 'Mr', 'test', 'Tm', '+91','9876543210',
           'test@example.com', '123 MG Road, Bangalore', '560001',
           '{"loanAmount": 700000, "tenure": 36}', 1, '2025-07-20 10:00:00', '2025-07-20 10:00:00'
       );


-- OTP for mobile/Email
INSERT INTO otp (recipient, otp, mode, used)
VALUES ('user1@example.com', '123456', 'email', false);

INSERT INTO otp (recipient, otp, mode, used)
VALUES ('9876543210', '654321', 'sms', false);


INSERT INTO customerfeedback (applicationid, feedback) VALUES
                                                           (1, 'Very smooth onboarding process, user-friendly interface.'),
                                                           (2, 'Took longer than expected to complete the form.'),
                                                           (2, 'Excellent support from Relationship Manager.'),
                                                           (1, 'Overall a good experience. Easy and clear instructions.');

INSERT INTO rmuser (rmid,name, password, role) VALUES
                                                   ('anwar','Anwar sk', 'password', 'RM'),
                                                   ('bharat','Bharat', 'password', 'RM'),
                                                   ('rahul','Rahul kumar', 'password', 'RM'),
                                                   ('nikhil','Nikhil', 'password', 'RM'),
                                                   ('arpit','Arpit', 'password', 'RM'),
                                                   ('admin','Admin', 'password', 'ADMIN'),
                                                   ('abhedya','Abhedya', 'password', 'RM');




-- Sample data: applicationdocuments
INSERT INTO applicationdocuments (id, applicationid, pagenumber, fieldname, filename, filetype, filecontent)
VALUES
    (1, 1, 2, 'panProof', 'pan.jpg', 'image/jpeg', '66616B6562696E6172796461746131'),
    (2, 2, 2, 'incomeDoc', 'income.pdf', 'application/pdf', '66616B6562696E6172796461746132');

-- Sample data: rmmapping
INSERT INTO rmmapping (rmid, rmname, pincode, journeytype)
VALUES
    ('rahul', 'Rahul kumar', '500090', 'Insurance'),
    ('nikhil', 'nikhil ', '500090', 'AccountOpening'),
    ('anwar', 'Anwar sk', '500090', 'Loan'),
    ('arpit', 'arpit', '500091', 'Loan'),
    ('bharat', 'Bharat', '500091', 'Insurance'),
    ('Abhedya', 'Abhedya Ayush ', '500091', 'AccountOpening');

-- Sample data: journeytemplate for Loan
INSERT INTO journeytemplate (journeytype, version, templatedata)
VALUES (
           'Loan',
           'v1',
           '{
             "pages": [
               {
                 "pageIndex": 1,
                 "title": "Loan Details",
                 "fields": [
                   {
                     "label": "Loan Amount",
                     "name": "loanAmount",
                     "type": "number",
                     "required": true,
                     "validation": { "min": 10000, "max": 1000000 }
                   },
                   {
                     "label": "Loan Purpose",
                     "name": "loanPurpose",
                     "type": "select",
                     "options": ["Home", "Car", "Education", "Business"],
                     "required": true
                   }
                 ]
               },
               {
                 "pageIndex": 2,
                 "title": "Employment Info",
                 "fields": [
                   {
                     "label": "Employment Type",
                     "name": "employmentType",
                     "type": "radio",
                     "options": ["Salaried", "Self-Employed"],
                     "required": true
                   },
                   {
                     "label": "Monthly Income",
                     "name": "monthlyIncome",
                     "type": "number",
                     "required": true,
                     "validation": { "min": 1000 }
                   },
                   {
                     "label": "Income Proof Document",
                     "name": "incomeProof",
                     "type": "file",
                     "required": true,
                     "accept": [".pdf", ".jpg", ".jpeg"]
                   }
                 ]
               },
               {
                 "pageIndex": 3,
                 "title": "Identification Upload",
                 "fields": [
                   {
                     "label": "PAN Number",
                     "name": "pan",
                     "type": "text",
                     "required": true,
                     "validation": { "regex": "^[A-Z]{5}[0-9]{4}[A-Z]$" }
                   },
                   {
                     "label": "PAN Document Upload",
                     "name": "panDocument",
                     "type": "file",
                     "required": true,
                     "accept": [".pdf", ".jpg"]
                   }
                 ]
               }
             ]
           }'
       );
