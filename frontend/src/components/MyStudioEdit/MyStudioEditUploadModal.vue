<!-- 아직 작성하지 않은 코드입니다. -->
<template>
  <div
    class="modal fade"
    id="UploadModal"
    tabindex="-1"
    aria-labelledby="UploadModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">사진 올리기</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="closeModal"
          ></button>
        </div>
        <div class="modal-body">
          <div class="form-group mb-2">
            <label for="exampleFormControlInput1">태그 입력</label>
            <input
              v-model="text"
              type="text"
              id="exampleFormControlInput1"
              class="form-control text-center"
            />
          </div>
          <div class="mb-3" id="imgFileUploadInsertWrapper">
            <input
              @change="changeFile"
              type="file"
              id="inputFileUploadInsert"
              multiple
            />
            <div id="imgFileUploadInsertThumbnail" class="thumbnail-wrapper">
              <!-- vue way img 를 만들어서 append 하지 않고, v-for 로 처리 -->
              <img
                v-for="(file, index) in fileList"
                v-bind:src="file"
                v-bind:key="index"
              />
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button
            @click="uploadFile"
            data-bs-dismiss="modal"
            class="btn btn-sm btn-primary btn-outline"
            type="button"
          >
            업로드
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/assets/js/axios.js'

export default {
  name: 'UploadModal',
  data() {
    return {
      attachFile: false,
      files: '',
      fileList: [],
      tag: ['우정', '웨딩'],
      text: '',
    }
  },
  methods: {
    initUI() {
      this.attachFile = false
      this.fileList = []
      document.querySelector('#inputFileUploadInsert').value = ''
    },
    changeFile(fileEvent) {
      this.files = fileEvent.target
      if (fileEvent.target.files && fileEvent.target.files.length > 0) {
        for (var i = 0; i < fileEvent.target.files.length; i++) {
          const file = fileEvent.target.files[i]
          this.fileList.push(URL.createObjectURL(file))
        }
      }
    },
    uploadFile() {
      // file upload
      // let dataArray = new Array()
      let data = new FormData()
      data.append('data', this.text)
      // data.append('file', dataArray)

      let cnt = this.files.files.length
      for (var i = 0; i < cnt; i++) {
        data.append('file', this.files.files[i])
      }
      console.log('upload pending files')
      console.log(data)
      return http
        .post('/studioedit/photo', data)
        .then((res) => {
          console.log(res)
          this.toastSuccess('업로드하였습니다.')
          this.closeModal()
        })
        .catch((err) => {
          console.log(err)
          this.toastDanger('업로드하는데 실패하였습니다.')
          this.closeModal()
        })
    },
    closeModal() {
      this.$emit('call-parent-close') // no parameter
    },
    toastSuccess(text) {
      this.$moshaToast(text, {
        type: 'success',
        position: 'bottom-right',
        timeout: 3000,
        showIcon: true,
      })
    },
    toastDanger(text) {
      this.$moshaToast(text, {
        type: 'danger',
        position: 'bottom-right',
        timeout: 3000,
        showIcon: true,
      })
    },
  },
  mounted() {
    let $this = this
    this.$el.addEventListener('show.bs.modal', function () {
      $this.initUI()
    })
  },
}
</script>

<style>
.thumbnail-wrapper {
  margin-top: 5px;
}

.thumbnail-wrapper img {
  width: 100px !important;
  margin-right: 5px;
  max-width: 100%;
}
</style>
