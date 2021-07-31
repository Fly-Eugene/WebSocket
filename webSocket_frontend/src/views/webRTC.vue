<template>
  <div>
    <video id="localVideo" autoplay playsinline controls="true"/>
  </div>
</template>

<script>
export default {
  name: "webRTC",

  setup() {

      // 모든 카메라와 마이크를 확인하고 사용자에게 피드백 하기 위함임 => enumerateDevices 사용
      // MediaDevicesInfo 에는 어떤 유형의 미디어 장치인지를 나타내는 것이 kind 라는 속성에 포함됨
      async function getConnectedDevices(type) {
        const devieces = await navigator.mediaDevices.enumerateDevices()
        return devieces.filter(device => device.kind === type)
      }


      // 카메라, 녹화 장치가 추가되었을 때, 변경을 감지하기 위한 함수
      function updateCameraList(cameras) {
        const listElement = document.querySelector('select#availableCameras')
        listElement.innerHTML = ''
        cameras.map(camera => {
          const cameraOption = document.createElement('option')
          cameraOption.label = camera.label
          cameraOption.value = camera.deviceId
        }).forEach(cameraOption => listElement.add(cameraOption))
      }
      
      const videoCameras = getConnectedDevices('videoinpt')
      console.log('Cameras found:', videoCameras)
      // updateCameraList(videoCameras)

      // mediaDevices에 이벤트 리스너를 달아놓는다
      navigator.mediaDevices.addEventListener('devicechange', event => {
        const newCameraList = getConnectedDevices('video')
        updateCameraList(newCameraList)
        console.log(event)
      })

      async function playVideoFromCamera() {
        try {
            const constraints = {'video': true}
            const stream = await navigator.mediaDevices.getUserMedia(constraints)
            const videoElement = document.querySelector('video#localVideo')
            videoElement.srcObject = stream
        } catch(error) {
            console.error('Error opening video camera.', error)
        }
      }

      // 특정 카메라를 minWidth 와 minHeight 를 지정해서 반환하기
      async function openCamera(cameraId, minWidth, minHeight) {
        const constraints = {
            'audio': {'echoCancellation': true},
            'video': {
                'deviceId': cameraId,
                'width': {'min': minWidth},
                'height': {'min': minHeight}
                }
            }

        return await navigator.mediaDevices.getUserMedia(constraints);
      }

      // const cameras = getConnectedDevices('videoinput');
      // const stream = openCamera(cameras[0].deviceId, 1280, 720);

      playVideoFromCamera()


    return {
      playVideoFromCamera,
      openCamera,

    }
  }


}
</script>

<style>

</style>