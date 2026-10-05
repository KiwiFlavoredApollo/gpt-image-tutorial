curl.exe -X POST "http://localhost:8080/api/images/clothing-transfer" `
  -F "person=@C:\path\to\person.jpg" `
  -F "clothing=@C:\path\to\clothing.jpg" `
  -o result.png