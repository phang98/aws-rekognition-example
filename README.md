## Synopsis

This is a project to learn a quick Java sample from aws-rekognition


## Motivation

Just for fun and learn aws-rekognition


## API Reference

http://docs.aws.amazon.com/rekognition/latest/dg/API_Reference.html


## Tests

* Just create an IAM account.
    * Copy the Access key ID
    * Copy the Secret access key
* Install [aws-cli](https://aws.amazon.com/cli/)
    * aws configure (enter the acess key id and secret access key)
* mvn clean install
* java -jar target/FaceDetection-1.0-SNAPSHOT.jar
 

## What’s next?
- [x] Try compareFacesMatch feature.
- [ ] Create a tool to update face detail on the image.
    - [ ] Use JS library, to paste your image from clipboard/from file. Then highlight the detail based on the facedetail return from the aws-reckognition call.
- [ ] Use python instead and run in raspberry pi.


## Contributors

Everyone are welcome

## License

Free for use.