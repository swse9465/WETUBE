<template>
    <div>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">제목</th>
                    <th scope="col">좋아요</th>
                    <th scope="col">조회수</th>
                    <th scope="col">작성일</th>
                </tr>
            </thead>
            <tbody> 
                <tr v-for="board in boards" :key=board.id :board="board">
                    <th scope="row">2</th>
                    <td>{{ board.subject }}</td>
                    <td>{{ board.likes }}</td>
                    <td>{{ board.views }}</td>
                    <td>{{ getDate(board) }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    props: ['boards'],
    data() {
        return {
        }
    },
    methods: {
        getDate(board) {
            return board.createDate.slice(0,10)
        }
    },
    // computed: {
    //     date() {
            
    //     }
    // }

}
</script>

<style>

</style>