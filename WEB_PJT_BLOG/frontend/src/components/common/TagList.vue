<template>
    <div id="tag">
        <!-- <SearchBar/> -->
        <!-- style="position: sticky" -->
        <div class="tag-list-wrap">
            <h2>인기 태그</h2>
            <ul class="list-group">
                <li v-for="tag in tags" @click ="move(tag.tagName)" :key="tag.id" class="list-group-item d-flex justify-content-between align-items-center">
                        # {{ tag.tagName }}
                    <span class="badge badge-primary badge-pill">{{tag.count}}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import '../../assets/css/common.scss'
import axios from 'axios'
import constants from '../../lib/constants'
//import SearchBar from './SearchBar'
export default {
    //components: {SearchBar},
    data() {
        return {
            tags: []
        }
    },
    methods: {
        getTags: function () {
        //   axios.get(constants.baseUrl+'/boards/tag')
          axios.get(constants.baseUrl+'/tags/popular', {params: {_limit:3, _page: 1}})
            .then(res => {
              this.tags = res.data
            })
            .catch(err => console.error(err))
        },
        move : function(tagName){
              const data={
                           keyword : tagName,
                           filter : "tag"
                       }
                       this.$emit('getTag',data)
            
            
        }
    },
    created: function() {
        this.getTags()
    }
}
</script>

<style>
li{
    cursor: pointer;
}
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');
            @font-face {
    font-family: 'IBMPlexSansKR-Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Bold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
            
</style>