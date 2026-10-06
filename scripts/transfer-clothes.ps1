curl.exe -X POST "http://localhost:8080/api/images/clothing-transfer" `
  -F "person=@person.png" `
  -F "clothing=@clothing.png" `
  -o result.png
