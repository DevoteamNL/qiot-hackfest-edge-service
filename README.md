# air-quality-edge project 
![Master Branch](https://github.com/DevoteamNL/qiot-hackfest-edge-service/workflows/Master%20Branch/badge.svg)

## Technical rundown

### Cross compilation

We use the `Dockerfile.native.mulitarch` container along with GitHub actions to build the containers natively upon commit to master.
For any feature branch, we just compile and see if things run. :-) 
