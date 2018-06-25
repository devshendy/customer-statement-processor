<template>
  <div class="text-center" style="border: 1px solid #8F8F8F">
    <div class="q-pa-sm q-pl-md q-pr-md bg-secondary">
      <strong class="text-white">Validation Result</strong>
    </div>

    <div class="col-12 q-pa-md" v-if="noFileSelected">
      Please select a file to display its validation result.
    </div>

    <table class="col-12 text-left q-pl-md q-pr-md" v-else>
      <thead>
        <tr>
          <th scope="col" width="25%"></th>
          <th width="75%"></th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row" class="q-pa-sm">File Name</th>
          <td>{{ file.name }}</td>
        </tr>
        <tr>
          <th scope="row" class="q-pa-sm">Upload Date</th>
          <td>{{ file.uploadDate }}</td>
        </tr>
        <tr>
          <td colspan="2"><hr></td>
        </tr>
        <tr>
          <td colspan="2" class="text-center" v-if="file.status == 'IN_PROGRESS'">
            <div class="q-mt-md q-mb-md q-subheading text-secondary">
              <p>Validation is in progress</p>
              <q-spinner-ios color="secondary" size="25" />
            </div>
          </td>

          <td colspan="2" class="q-pt-sm q-pb-sm" v-else>
            <ul style="list-style: none; padding: 0 0 0 20px;">
              <li class="q-pt-sm q-pb-sm">
                <div class="row justify-start">
                  <div class="col-1">
                    <q-icon name="far fa-check-circle" color="positive" size="20px" />
                  </div>
                  <div class="col-11 text-left">
                    <p>All transaction references are unique.</p>
                    <p>
                      All transaction references are unique
                      All transaction references are unique
                      All transaction references are unique
                      All transaction references are unique
                    </p>
                  </div>
                </div>
              </li>

              <li class="q-pt-sm q-pb-sm">
                <div class="row justify-start">
                  <div class="col-1">
                    <q-icon name="fas fa-exclamation-circle" color="negative" size="20px" />
                  </div>
                  <div class="col-11 text-left">
                    <p class="text-negative">The end balance is valid for all records.</p>
                    <p>
                      The end balance for all is valid
                      The end balance for all is valid
                      The end balance for all is valid
                      The end balance for all is valid
                    </p>
                  </div>
                </div>
              </li>
            </ul>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'ValidationResult',
  props: {
    file: Object,
    default: () => null
  },
  data () {
    return {
      selectedFileIndex: null
    }
  },
  computed: {
    noFileSelected () {
      return Object.keys(this.file).length === 0
    }
  }
}
</script>

<style>
table {
    margin: auto;
    width: calc(100% - 40px);
}
</style>
