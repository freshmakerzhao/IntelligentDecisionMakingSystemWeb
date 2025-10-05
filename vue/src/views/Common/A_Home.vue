<template>
    <div>
        <div style="margin: 10px 0">
            <!-- 搜索框 suffix-icon内嵌图标 primary蓝色按钮 v-model绑定参数-->
            <el-input style="width: 200px" placeholder="请输入防疫物品名称" suffix-icon="el-icon-search" v-model="epidemicName"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>
        <!--        tableData存储表格数据-->
        <!--        header-cell-class-name设置固定表格样式-->
        <!--        selection-change选择时触发-->
        <el-table
                :data="tableData"
                border stripe
                :header-cell-class-name="'headerBg'"
                :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}"
        >
            <el-table-column prop="id" label="编号" ></el-table-column>
            <el-table-column prop="userName" label="用户名称" ></el-table-column>
            <el-table-column prop="epidemicName" label="物品名称" ></el-table-column>
            <el-table-column prop="epidemicPrice" label="单价" ></el-table-column>
            <el-table-column prop="nums" label="数量" ></el-table-column>
            <el-table-column prop="epidemicTotal" label="总价" ></el-table-column>
            <el-table-column prop="createtime" label="下单时间" :formatter="formatDate"></el-table-column>
        </el-table>
        <!--              底部页码等信息-->
        <!--              @size-change="handleSizeChange" 动态绑定，获取页面显示条数-->
        <!--              @current-change="handleCurrentChange" 动态绑定，获取页面所在页数-->
        <!--              :current-page="pageNum" 动态显示当前页-->
        <!--              :page-sizes="[5, 10, 15, 20]" 设置每页显示个数-->
        <!--              :page-size="10" 默认显示个数-->
        <!--              layout="total, sizes, prev, pager, next, jumper" 从左到右顺序-->
        <!--              :total="400" 总个数-->
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[2, 5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return{
                tableData: [],
                total: 0,
                pageNum: 1, // 当前页，默认为1
                pageSize: 5, // 每页显示数量，默认5
                epidemicName: '', // 搜索品种
            }
        },
        created() { // 加载时触发
            this.load();
        },
        methods:{
            // 从后台获取数据，并在页面中赋值
            load() {
                this.request.get("/epidemic/epidemicOrder/page", {
                    // 对应后台参数名称，将页面默认信息传到后台，获取表中数据
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        epidemicName: this.epidemicName,
                    }
                }).then(res => {
                    // 获取到数据后，request打包，返回前台
                    this.tableData = res.data.records;
                    this.total = res.data.total;
                })
            },
            // 重置搜索框
            reset() {
                this.epidemicName = "";
                // 重置搜索框后，重新加载表格
                this.load();
            },
            //  改变当前页码
            handleCurrentChange(pageNum) { //
                this.pageNum = pageNum;
                this.load();
            },
            //  改变当前每页容量
            handleSizeChange(pageSize) {
                this.pageSize = pageSize;
                this.load();
            },
            // 时间格式化
            formatDate(row, column) {
                // 获取单元格数据
                let data = row[column.property];
                if(data == null) {
                    return null
                }
                let dt = new Date(data);
                return dt.getFullYear() + '年' + (dt.getMonth() + 1) + '月' + dt.getDate() + '日';
            }
        }
    }

</script>

<style>
    .headerBg {
        background: #eee!important;
    }
</style>