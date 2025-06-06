import type { RequestOptions } from '@@/plugin-request/request';
import type { RequestConfig } from '@umijs/max';
import { message, notification } from 'antd';
import { history, Link } from '@umijs/max';

// 错误处理方案： 错误类型
enum ErrorShowType {
  SILENT = 0,
  WARN_MESSAGE = 1,
  ERROR_MESSAGE = 2,
  NOTIFICATION = 3,
  REDIRECT = 9,
}

// 与后端约定的响应数据格式
interface ResponseStructure {
  code: number;
  success: boolean;
  result: any;
  message?: string;
}

const loginPath = '/user/login';
// @ts-ignore
/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const errorConfig: RequestConfig = {
  // 错误处理： umi@3 的错误处理方案。
  errorConfig: {
    // 错误抛出
    errorThrower: (res: ResponseStructure) => {
      const { code, success, result, message } = res as unknown as ResponseStructure;
      if (code === 401) {
        localStorage.removeItem('token');
        history.push(loginPath);
        // message.warning('无效的会话，或者会话已过期，请重新登录。');
        console.log('无效的会话，或者会话已过期，请重新登录。')
        return;
      }
      if (!res.success) {
        const error: any = new Error(res.message);
        error.name = 'BizError';
        error.info = { code, success, result, message };
        throw error; // 抛出自制的错误
      }
    },
    // 错误接收及处理
    errorHandler: (error: any, opts: any) => {
      if (opts?.skipErrorHandler) throw error;
      // 我们的 errorThrower 抛出的错误。
      if (error.name === 'BizError') {
        const errorInfo: ResponseStructure | undefined = error.info;
        // console.log('errorInfo==>',errorInfo)
        if (errorInfo) {
          switch (errorInfo.code) {
            case 500:
              // do nothing
              message.error(error.message);
              break;
            case 401:
              // console.log('无效的会话，或者会话已过期，请重新登录。')
              localStorage.removeItem('token');
              history.push(loginPath);
              message.warning('无效的会话，或者会话已过期，请重新登录。');
              break;
            default:
              message.error(error.message);
          }
        }
      } else if (error.response) {
        // Axios 的错误
        // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
        message.error(`Response status:${error.response.status}`);
      } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        // \`error.request\` 在浏览器中是 XMLHttpRequest 的实例，
        // 而在node.js中是 http.ClientRequest 的实例
        message.error('None response! Please retry.');
      } else {
        // 发送请求时出了点问题
        message.error('Request error, please retry.');
      }
    },
  },

  // 请求拦截器
  requestInterceptors: [
    async (config: RequestOptions) => {
      const token = localStorage.getItem('token');
      return {
        ...config,
        headers: {
          ...config.headers,
          ...(token ? { Authorization: token } : {}),
        },
      };
    },
  ],

  // 响应拦截器
  responseInterceptors: [
    (response) => {
      return response;
    },
  ],
};
