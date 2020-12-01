<template>
<div id="search">
    <div class="input-group">
        <select class="selectpicker" v-model="option">
            <option value=""  @click="onInput" selected>전체</option>
            <option value="subject">제목</option>
            <option value="nickname">닉네임</option>
            <option value="tag">태그</option>
        </select>
        <input type="text" name="search" class="form-control" v-model="keyword" @keypress.enter="onInput"/>
    </div>
   
</div>
</template>

<script>
import '../../assets/css/common.scss'
export default {
    data() {
        return {
            keyword: '',
            option: ''
        }
    },
    methods: {
        onInput() {
            if(this.option==''){
                this.keyword=''  //전체조회니까 keyword 없애기 
                 const data={
                           keyword : this.keyword,
                           filter : this.option
                       }
                       this.$emit('search',data)
            }
            else if (this.keyword !=='') {
                       // this.$router.push({ name: 'BoardSearchList', params: {filter: this.option, keyword: this.keyword }})
                        //this.$router.push({ path: '/list', params: {filter: this.option, keyword: this.keyword ,limit:0,offset:0}})
                       const data={
                           keyword : this.keyword,
                           filter : this.option
                       }
                       this.$emit('search',data)
            }
        }
    }
}
</script>

<style>

</style>