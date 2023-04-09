<template>
  <div style="color:#666">
    <div>
      <div class="pd-10" style="color: darkorange ;cursor:pointer">{{ article.name }}</div>
      <div style="font-size: 10px">
        <i class="el-icon-user-solid"></i><span>{{article.user}}</span>
        <i class="el-icon-time" style="margin-left: 10px"></i><span>{{article.time}}</span>
      </div>
      <div style="width: 60%">
        <mavon-editor
            class="md"
            :value="article.content"
            :subfield="false"
            :defaultOpen="'preview'"
            :toolbarsFlag="false"
            :editable="false"
            :scrollStyle="true"
            :ishljs="true"
        />
      </div>
    </div>


  </div>
</template>

<script>


export default {
  name: "Article",
  data() {
    return {
      article:{}

    }
  },
  created() {
    this.load()
  },
  methods: {

    load() {
      const id=this.$route.query.id
      this.request.get("/article/"+id).then(res => {
        console.log(res)
        this.article = res.data
      })

    }
  }
}

</script>

<style scoped>



</style>