<template>
  <q-page class="row justify-center">
    <div class="col-12 q-mt-lg" style="margin-top: 100px">
      <div class="row justify-center">
        <div class="col-7 q-mr-md">
          <validation-table></validation-table>
          <!-- <validation-result
            :file="selectedFile"
            :valid="false"
          /> -->
        </div>

        <!-- <div class="col-4 text-center">
          <div class="upload-btn-wrapper col-12">
            <q-btn
              class="full-width"
              icon="add" color="primary"
              label="Upload File To Validate"
              :loading="loading"
            />

            <input type="file" name="myfile" accept="text/csv,text/xml" @change="fileAdded" />
          </div>

          <div class="row justify-center">
            <div class="col-12 q-mt-sm" v-if="uploadedFileList.length == 0">
              No File(s) Uploaded Yet.
            </div>

            <ul
              class="-m-uploaded-file-list col-12 q-mt-sm text-left"
              style="list-style: none"
              v-else
            >
              <li
                v-for="(file, index) in uploadedFileList"
                class="-m-file-list-item q-pa-sm q-pl-md q-pr-md"
                :class="{ '-m-item-selected': selectedFileIndex == index }"
                :key="`file-${index}`"
                @click="selectedFileIndex = index"
              >
                <span>
                  <q-spinner-mat color="primary" size="20px" v-if="file.status == uploadedFileStatus.IN_PROGRESS"/>
                  <q-icon name="fas fa-exclamation-circle" size="20px" color="red" v-if="file.status == uploadedFileStatus.DONE_WITH_ERROR"/>
                  <q-icon name="far fa-check-circle" size="20px" color="lime-9" v-if="file.status == uploadedFileStatus.DONE_WITH_SUCCESS"/>
                </span>
                <span class="-m-file-name q-pl-md q-pr-md">{{ file.name }}</span>
              </li>
            </ul>
          </div>
        </div> -->

      </div>
    </div>
  </q-page>
</template>

<style>
  .-m-uploaded-file-list {
    padding-left: 0;
  }

  .-m-file-list-item.-m-item-selected {
    background-color: #F9CB55;
  }

  .-m-file-list-item.-m-item-selected .-m-file-name {
    font-weight: bold;
  }

  .upload-btn-wrapper {
    position: relative;
    overflow: hidden;
    cursor: pointer;
  }

  .upload-btn-wrapper input[type=file] {
    font-size: 100px;
    position: absolute;
    left: 0;
    top: 0;
    opacity: 0;
  }
</style>

<script>
import ValidationResult from './ValidationResult'
import ValidationTable from './ValidationTable'

const UPLOADED_FILE_STATUS = {
  IN_PROGRESS: 'IN_PROGRESS',
  DONE_WITH_SUCCESS: 'DONE_WITH_SUCCESS',
  DONE_WITH_ERROR: 'DONE_WITH_ERROR'
}

export default {
  name: 'PageIndex',
  components: {
    ValidationResult,
    ValidationTable
  },
  data () {
    return {
      loading: false,
      resultList: [],
      files: [],
      uploadedFileStatus: UPLOADED_FILE_STATUS,
      selectedFileIndex: null,
      uploadedFileList: []
    }
  },
  computed: {
    selectedFile () {
      if (this.selectedFileIndex === null) return {}
      return this.uploadedFileList[this.selectedFileIndex]
    }
  },
  methods: {
    fileAdded (e) {
      const fileList = e.target.files

      if (!fileList.length) return

      const file = fileList[0]
      const formData = new FormData()

      // const data = {
      //   uploadedFile: file
      // }

      const config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }

      formData.append('uploadedFile', file)
      this.addFileToUploadeFileList(file)

      console.log(formData.get('uploadedFile'))

      this.$axios.post('http://localhost:9090/uploads', formData, config)
        .then(response => console.log(response))
        .catch(error => console.error(`APP-ERROR: ${error}`))
    },
    addFileToUploadeFileList (uploadedFile) {
      this.uploadedFileList.push({
        name: uploadedFile.name,
        status: UPLOADED_FILE_STATUS.IN_PROGRESS,
        selected: false,
        uploadDate: this.getUploadDateForValidationResultScreen()
      })
    },
    getUploadDateForValidationResultScreen () {
      const date = new Date()
      return `${date.getDate()} ${date.toLocaleString('en-us', {month: 'short'})} ${date.getFullYear()}`
    }
  }
}
</script>
