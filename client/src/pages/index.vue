<template>
  <q-page class="row justify-center">
    <!-- <img alt="Quasar logo" src="~assets/quasar-logo-full.svg"> -->
    <div class="col-12 text-center q-pt-lg">
      <q-btn label="Hit Spring Reactive Endpoint" @click="hitApi"/>

      <div class="col-4">
        <ul>
          <li v-for="(item, index) in resultList" :key="index">{{ item }}</li>
        </ul>
      </div>
    </div>
  </q-page>
</template>

<style>
</style>

<script>
export default {
  name: 'PageIndex',
  data () {
    return {
      result: '',
      resultList: []
    }
  },
  methods: {
    hitApi () {
      this.$axios.get('/api/hello_webflux')
        .then(response => {
          this.resultList.push(response.data)
        })
        .catch(error => {
          console.error(error)
        })
    }
  }
}
</script>
