<template>
  <div class="app-container">
    <div
      style="line-height: 40px; font-weight: 700; font-size: 22px; color: #a85a11; margin-left: 10px;"
    >讲师列表</div>
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button plain="true" type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button plain="true" type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="items"
      v-loading="listLoading"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{ (current - 1) * limit + scope.$index + 1 }}</template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="160" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          <!-- ===判断类型和值 -->
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" plain="true" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button
            type="danger"
            size="mini"
            plain="true"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="current"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
import teacher from "@/api/edu/teacher";

export default {
  data() {
    return {
      items: null,
      current: 1,
      limit: 10,
      total: 0,
      teacherQuery: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //这里有细节的，传过来调整的页面，赋值给current，传给后台
    getList(current = 1) {
      this.current = current;
      console.log(current);

      teacher
        .getTeacherList(this.current, this.limit, this.teacherQuery)
        .then((resp) => {
          this.items = resp.data.records.records;
          this.total = resp.data.total;
          console.log(this.items);
        })
        .catch((error) => {
          console.log("出问题了 - " + error);
        });
    },

     resetData() {
    //清空按钮的方法
    //清空已输入数据
    this.teacherQuery = {};
    //查询所有  恢复未进行条件查询的状态
    this.getList();
  },
  removeDataById(id) {
    //删除讲师按钮的方法
    this.$confirm("此操作将永久删除讲师信息, 是否继续?", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }).then(() => {
      teacher.deleteTeacherById(id).then((response) => {
        //删除成功
        //提示信息
        this.$message({
          type: "success",
          message: "删除成功🤭",
        });
        //回到列表页面
        this.getList();
      })
    //   .catch(err=>{
    //      this.$message({
    //          type:"error",
    //          message: "败了┭┮﹏┭┮",
    //      })
    //   })
    });
    //点取消执行catch方法  用户体验角度
    //此处无需进行提示取消
    //框架在./utils/request.js封装好了提示error的方法
  },


  },

};
</script>